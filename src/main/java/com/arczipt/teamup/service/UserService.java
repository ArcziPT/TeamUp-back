package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.model.User;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface UserService {
    UserDTO findById(Long id);

    SearchResult<UserMinDTO> search(String username, ArrayList<String> projects, ArrayList<String> skills, Pageable pageable);

    boolean register(UserRegisterDTO userRegisterDTO);

    UserDTO findByUsername(String username);

    SearchResult<UserMinDTO> findWithUsernameLike(String pattern, Pageable pageable);

    SearchResult<UserMinDTO> findBySkillName(String skillName, Pageable pageable);

    boolean rateUser(Long id, String raterUsername);
    boolean unrateUser(Long id, String raterUsername);

    boolean isRated(Long id, String raterUsername);

    ArrayList<JobApplicationDTO> getJobApplications(String username);

    ArrayList<ProjectMemberDTO> getProjects(Long id);

    ArrayList<ProjectInvitationDTO> getInvitations(String username, Boolean waiting);
    boolean updateInvitationStatus(String username, Long id, Boolean accepted);

    ArrayList<IdAndNameDTO> getManagedProjects(Long id);
}
