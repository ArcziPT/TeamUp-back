package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.model.User;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface UserService {
    UserDTO findById(Long id);

    UserDTO findByUsername(String username);

    ArrayList<UserMinDTO> findWithUsernameLike(String pattern, Pageable pageable);

    ArrayList<UserMinDTO> findBySkillName(String skillName, Pageable pageable);

    boolean rateUser(Long id, String raterUsername);
    boolean unrateUser(Long id, String raterUsername);

    boolean isRated(Long id, String raterUsername);

    ArrayList<JobApplicationDTO> getJobApplications(String username);

    ArrayList<ProjectMinDTO> getProjects(String username);

    ArrayList<ProjectInvitationDTO> getInvitations(String username);
}
