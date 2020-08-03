package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.ProjectInvitationDTO;
import com.arczipt.teamup.repo.ProjectInvitationRepository;
import com.arczipt.teamup.service.ProjectInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/invitation")
public class ProjectInvitationController {

    private ProjectInvitationService projectInvitationService;

    @Autowired
    public ProjectInvitationController(ProjectInvitationService projectInvitationService){
        this.projectInvitationService = projectInvitationService;
    }

    @GetMapping("/{id}")
    public ProjectInvitationDTO getInvitation(@PathVariable Long id){
        return projectInvitationService.getInvitation(id);
    }

    @PostMapping("/{id}/accept")
    public String accept(Long id){
        projectInvitationService.accept(id);

        return "OK";
    }

    @PostMapping("/{id}/decline")
    public String decline(Long id){
        projectInvitationService.decline(id);

        return "OK";
    }
}
