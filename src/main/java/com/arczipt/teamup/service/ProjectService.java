package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;

import java.util.ArrayList;

public interface ProjectService {

    ProjectDTO findById(Long id);

    ProjectDTO findByName(String name);

    ArrayList<ProjectMinDTO> findWithNameLike(String pattern);

    ArrayList<UserMinDTO> getMembers(Long id);

    void sendInvitation(ProjectInvitationDTO projectInvitationDTO);

    ArrayList<ProjectInvitationMinDTO> getInvitations(Long id);

    ArrayList<JobPostingDTO> getJobPostings(Long id);

    void addJobPosting(Long id, JobPostingCreateDTO jobPosting);

    boolean isAdmin(Long projectId, String username);
}
