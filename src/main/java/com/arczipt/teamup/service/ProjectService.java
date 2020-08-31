package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface ProjectService {

    ProjectDTO findById(Long id);

    ProjectDTO findByName(String name);

    boolean createProject(ProjectCreateDTO projectDTO, String username);

    SearchResult<ProjectMinDTO> findWithNameLike(String pattern, Pageable pageable);

    ArrayList<ProjectMemberDTO> getMembers(Long id);

    boolean sendInvitation(ProjectInvitationCreateDTO projectInvitation);

    ArrayList<ProjectInvitationDTO> getInvitations(Long id);

    ArrayList<JobPostingDTO> getJobPostings(Long id, String username);

    boolean hasApplied(String username, Long projectId, Long postingId);

    boolean apply(String username, Long projectId, Long postingId);

    boolean addJobPosting(Long id, JobPostingCreateDTO jobPosting);

    boolean isAdmin(Long projectId, String username);

    boolean isMember(String username, Long id);

    boolean edit(Long id, ProjectDTO projectDTO);

    boolean removeMember(Long id, String username);

    boolean makeAdmin(Long id, String username);

    ArrayList<IdAndNameDTO> getDepartments(Long id);

    boolean addDepartment(Long id, String departmentName);
}
