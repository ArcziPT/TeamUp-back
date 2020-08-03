package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.JobApplicationDTO;

public interface JobApplicationService {

    JobApplicationDTO findById(Long id);

    void accept(Long appId);

    void decline(Long appId);

    void waitlist(Long appId);
}
