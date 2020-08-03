package com.arczipt.teamup.controller;

import com.arczipt.teamup.dto.JobApplicationDTO;
import com.arczipt.teamup.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobApp")
public class JobApplicationController {

    private JobApplicationService jobApplicationService;

    @Autowired
    public JobApplicationController(JobApplicationService jobApplicationService){
        this.jobApplicationService = jobApplicationService;
    }
}
