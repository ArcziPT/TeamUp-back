package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.security.AuthenticationProvider;
import com.arczipt.teamup.service.ProjectService;
import com.arczipt.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private ProjectService projectService;
    private AuthenticationProvider authProvider;
    private UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             UserService userService,
                             AuthenticationProvider authProvider){
        this.projectService = projectService;
        this.userService = userService;
        this.authProvider = authProvider;
    }

    @GetMapping("/search")
    public ArrayList<ProjectMinDTO> search(@RequestParam(defaultValue = "") String searchBy,
                                           @RequestParam(defaultValue = "") String pattern,
                                           @RequestParam(defaultValue = "") String sortBy,
                                           @RequestParam(defaultValue = "") String order,
                                           @RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "0") Integer size){

        if(searchBy.equals("") || pattern.equals("") || sortBy.equals("") || order.equals(""))
            return null; //TODO

        PageRequest pageRequest;
        switch (order){
            case "asc" -> pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            case "des" -> pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
            default -> throw new IllegalStateException("Unexpected value: " + order);
        }

        ArrayList<ProjectMinDTO> projects;
        switch (searchBy) {
            case "name" -> projects = projectService.findWithNameLike(pattern);
            default -> throw new IllegalStateException("Unexpected value: " + searchBy);
        }

        return projects;
    }

    @GetMapping("/")
    public ArrayList<ProjectMinDTO> getProjects(){
        String username = authProvider.getUsername();

        return userService.getProjects(username);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Long id){
        return projectService.findById(id);
    }

    @GetMapping("/{id}/isAdmin")
    public String isAdmin(@PathVariable Long id){
        return projectService.isAdmin(id, authProvider.getUsername()) ? "Y" : "N";
    }

    @PostMapping("/{id}/invitations")
    public String sendInvitation(@PathVariable Long id,  ProjectInvitationDTO projectInvitationDTO){
        projectService.sendInvitation(projectInvitationDTO);

        return "Y";
    }

    @GetMapping("/{id}/invitations")
    public ArrayList<ProjectInvitationDTO> getInvitations(@PathVariable Long id){
        return projectService.getInvitations(id);
    }

    @PostMapping("/{id}/postings")
    public String createJobPosting(@PathVariable Long id, @RequestParam JobPostingCreateDTO jobPosting){
        projectService.addJobPosting(id, jobPosting);
        return "Y";
    }

    @GetMapping("/{id}/postings")
    public ArrayList<JobPostingDTO> getJobPostings(@PathVariable Long id){
        return projectService.getJobPostings(id);
    }

    @GetMapping("/{projectId}/postings/{postingId}")
    public ArrayList<JobPostingDTO> getJobPosting(@PathVariable Long projectId, @PathVariable Long postingId){
        return new ArrayList<>();//TODO
    }
}
