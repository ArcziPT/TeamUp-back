package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.security.AuthenticationProvider;
import com.arczipt.teamup.service.ProjectService;
import com.arczipt.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public SearchResult<ProjectMinDTO> search(@RequestParam(defaultValue = "") String searchBy,
                                           @RequestParam(required = false, defaultValue = "") String pattern,
                                           @RequestParam(defaultValue = "") String sortBy,
                                           @RequestParam(defaultValue = "") String order,
                                           @RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer size){

        if(searchBy.equals("") || sortBy.equals("") || order.equals(""))
            return null; //TODO

        PageRequest pageRequest;
        switch (order){
            case "asc" -> pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            case "des" -> pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
            default -> throw new IllegalStateException("Unexpected value: " + order);
        }

        SearchResult<ProjectMinDTO> projects;
        switch (searchBy) {
            case "name" -> projects = projectService.findWithNameLike(pattern, pageRequest);
            default -> throw new IllegalStateException("Unexpected value: " + searchBy);
        }

        return projects;
    }

    @GetMapping("/")
    public ArrayList<ProjectMemberDTO> getProjects(){
        Long id = authProvider.getId();

        return userService.getProjects(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> createProject(@RequestBody ProjectCreateDTO projectDTO){
        String username = authProvider.getUsername();
        return ResponseEntity.ok(new StatusDTO(projectService.createProject(projectDTO, username)));
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Long id){
        return projectService.findById(id);
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @PostMapping("/{id}")
    public ResponseEntity<?> editProject(@PathVariable Long id, @RequestBody ProjectDTO project){
        return ResponseEntity.ok(new StatusDTO(this.projectService.edit(id, project)));
    }

    @GetMapping("/{id}/isAdmin")
    public ResponseEntity<?> isAdmin(@PathVariable Long id){
        return ResponseEntity.ok(new StatusDTO(projectService.isAdmin(id, authProvider.getUsername())));
    }

    @GetMapping("/{id}/isMember")
    public ResponseEntity<?> isMember(@PathVariable Long id){
        String username = authProvider.getUsername();
        return ResponseEntity.ok(new StatusDTO(projectService.isMember(username, id)));
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @PostMapping("/{id}/invitations")
    public ResponseEntity<?> sendInvitation(@PathVariable Long id, @RequestBody ProjectInvitationCreateDTO projectInvitation){
        return ResponseEntity.ok(new StatusDTO(projectService.sendInvitation(projectInvitation)));
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @GetMapping("/{id}/invitations")
    public ArrayList<ProjectInvitationDTO> getInvitations(@PathVariable Long id){
        return projectService.getInvitations(id);
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @PostMapping("/{id}/postings")
    public ResponseEntity<?> createJobPosting(@PathVariable Long id, @RequestBody JobPostingCreateDTO jobPosting){
        return ResponseEntity.ok(new StatusDTO(projectService.addJobPosting(id, jobPosting)));
    }

    @PostMapping("/{projectId}/postings/{postingId}")
    public ResponseEntity<?> apply(@PathVariable Long projectId, @PathVariable Long postingId){
        String username = authProvider.getUsername();

        return ResponseEntity.ok(new StatusDTO(projectService.apply(username, projectId, postingId)));
    }

    @GetMapping("/{projectId}/postings/{postingId}/hasApplied")
    public StatusDTO hasApplied(@PathVariable Long projectId, @PathVariable Long postingId){
        String username = authProvider.getUsername();
        return new StatusDTO(projectService.hasApplied(username, projectId, postingId));
    }

    @GetMapping("/{id}/postings")
    public ArrayList<JobPostingDTO> getJobPostings(@PathVariable Long id){
        String username = authProvider.getUsername();
        return projectService.getJobPostings(id, username);
    }

    @GetMapping("/{id}/members")
    public ArrayList<ProjectMemberDTO> getMembers(@PathVariable Long id){
        return projectService.getMembers(id);
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @DeleteMapping("/{id}/members")
    public ResponseEntity<?> removeMember(@PathVariable Long id, @RequestParam String username){
        return ResponseEntity.ok(new StatusDTO(projectService.removeMember(id, username)));
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @PostMapping("/{id}/makeAdmin")
    public ResponseEntity<?> makeAdmin(@PathVariable Long id, @RequestBody String username){
        return ResponseEntity.ok(new StatusDTO(projectService.makeAdmin(id, username)));
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @GetMapping("/{id}/departments")
    public ArrayList<IdAndNameDTO> getDepartments(@PathVariable Long id){
        return this.projectService.getDepartments(id);
    }

    @PreAuthorize("@authorizationFunctions.isAdmin(authentication, id)")
    @PostMapping("/{id}/departments")
    public ResponseEntity<?> addDepartment(@PathVariable Long id, @RequestBody String departmentName){
        return ResponseEntity.ok(new StatusDTO(projectService.addDepartment(id, departmentName)));
    }
}
