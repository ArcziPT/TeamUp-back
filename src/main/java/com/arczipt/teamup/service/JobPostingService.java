package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.JobApplicationDTO;
import com.arczipt.teamup.dto.JobPostingDTO;
import com.arczipt.teamup.dto.JobPostingMinDTO;
import com.arczipt.teamup.dto.SearchResult;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface JobPostingService {
    JobPostingDTO get(Long id, String username);

    SearchResult<JobPostingMinDTO> search(String title, String project, String roleName, ArrayList<String> departments, Pageable pageable);

    SearchResult<JobApplicationDTO> getApplications(Long id, Pageable pageable);

    boolean updateApplicationStatus(Long postingId, Long appId, Boolean accepted);
}
