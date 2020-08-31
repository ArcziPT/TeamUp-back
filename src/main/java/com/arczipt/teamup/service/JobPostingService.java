package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.JobApplicationDTO;
import com.arczipt.teamup.dto.JobPostingDTO;
import com.arczipt.teamup.dto.SearchResult;
import org.springframework.data.domain.Pageable;

public interface JobPostingService {
    JobPostingDTO get(Long id, String username);

    SearchResult<JobApplicationDTO> getApplications(Long id, Pageable pageable);

    boolean updateApplicationStatus(Long postingId, Long appId, Boolean accepted);
}
