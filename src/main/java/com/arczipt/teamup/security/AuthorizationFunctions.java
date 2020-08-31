package com.arczipt.teamup.security;

import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.repo.JobPostingRepository;
import com.arczipt.teamup.repo.ProjectRepository;
import com.arczipt.teamup.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authorizationFunctions")
public class AuthorizationFunctions {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private JobPostingRepository jobPostingRepository;

    AuthorizationFunctions(UserRepository userRepository,
                           ProjectRepository projectRepository,
                           JobPostingRepository jobPostingRepository){
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.jobPostingRepository = jobPostingRepository;
    }

    /**
     *
     * @param authentication
     * @param projectId
     * @return true if calling user is admin of project with id = projectId
     */
    public boolean isAdmin(Authentication authentication, Long projectId){
        return userRepository.findUserByUsername(authentication.getName()).getProjectMember().stream().filter(ProjectMember::getAdmin).
                anyMatch(pm -> pm.getProject().getId().equals(projectId));
    }

    public boolean canManagePosting(Authentication authentication, Long postingId){
        return jobPostingRepository.findById(postingId).map(jobPosting -> {
            return jobPosting.getProject().getMembers().stream().filter(ProjectMember::getAdmin).anyMatch(pm -> pm.getUser().getUsername().equals(authentication.getName()));
        }).orElse(false);
    }
}
