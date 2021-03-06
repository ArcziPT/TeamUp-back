package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.repo.JobPostingRepository;
import com.arczipt.teamup.security.AuthenticationProvider;
import com.arczipt.teamup.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.AuthProvider;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/postings")
public class JobPostingController {

    private JobPostingService jobPostingService;
    private AuthenticationProvider authProvider;

    @Autowired
    JobPostingController(JobPostingService jobPostingService,
                         AuthenticationProvider authProvider){
        this.jobPostingService = jobPostingService;
        this.authProvider = authProvider;
    }

    @GetMapping("/search")
    public SearchResult<JobPostingMinDTO> search(@RequestParam(required = false) String title,
                                                 @RequestParam(required = false) String project,
                                                 @RequestParam(required = false) String roleName,
                                                 @RequestParam(required = false) ArrayList<String> departments,
                                                 @RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "20") Integer size){
        return jobPostingService.search(title, project, roleName, departments, PageRequest.of(page, size));
    }

    /**
     * Get information about specified job posting.
     *
     * @param id - job posting's id
     * @return
     */
    @GetMapping("/{id}")
    public JobPostingDTO getJobPosting(@PathVariable Long id){
        String username = authProvider.getUsername();
        return jobPostingService.get(id, username);
    }

    /**
     * Get applications related to specified job posting.
     * Allowed only for administrator of a project.
     *
     * @param id
     * @param size
     * @param page
     * @return
     */
    @PreAuthorize("@authorizationFunctions.canManagePosting(authentication, id)")
    @GetMapping("/{id}/applications")
    public SearchResult<JobApplicationDTO> getApplications(@PathVariable Long id, @RequestParam Integer size, @RequestParam Integer page){
        return jobPostingService.getApplications(id, PageRequest.of(page, size));
    }

    /**
     * Update status of application (accepted/declined).
     * Allowed only for administrator of a project.
     *
     * @param postingId
     * @param appId
     * @param accepted
     * @return
     */
    @PreAuthorize("@authorizationFunctions.canManagePosting(authentication, postingId)")
    @PutMapping("/{postingId}/applications/{appId}")
    public ResponseEntity<?> updateApplicationsStatus(@PathVariable Long postingId, @PathVariable Long appId, @RequestParam Boolean accepted){
        return ResponseEntity.ok(new StatusDTO(jobPostingService.updateApplicationStatus(postingId, appId, accepted)));
    }
}
