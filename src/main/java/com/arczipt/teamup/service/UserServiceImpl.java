package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.model.User;
import com.arczipt.teamup.repo.ProjectRepository;
import com.arczipt.teamup.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findById(Long id) {
        return userRepository.findById(id).map(UserDTO::new).orElseThrow();
    }

    @Override
    public UserDTO findByUsername(String username) {
        return new UserDTO(userRepository.findUserByUsername(username));
    }

    /**
     * Find users with usernames matching pattern.
     *
     * @param pattern - username pattern
     * @return list of users
     */
    @Override
    public ArrayList<UserMinDTO> findWithUsernameLike(String pattern, Pageable pageable) {
        return (ArrayList<UserMinDTO>) userRepository.findUsersWithUsernameLike(pattern, pageable).stream().map(UserMinDTO::new).collect(Collectors.toList());
    }

    @Override
    public ArrayList<UserMinDTO> findBySkillName(String skillName, Pageable pageable) {
        return (ArrayList<UserMinDTO>) userRepository.findUsersBySkillName(skillName, pageable).stream().map(UserMinDTO::new).collect(Collectors.toList());
    }

    private boolean isRatedBy(User user, String raterUsername){
        return user.getRatedBy().stream().anyMatch(u -> u.getUsername().equals(raterUsername));
    }

    @Override
    public boolean rateUser(Long id, String raterUsername){
        Optional<User> op = userRepository.findById(id);

        //TODO: error handling
        //TODO: create specialized query
        if(op.isEmpty())
            throw new NullPointerException();

        User user = op.get();

        if(isRatedBy(user, raterUsername)){
            return false;
        }else{
            user.setRating(user.getRating() + 1);
            return true;
        }
    }

    @Override
    public boolean unrateUser(Long id, String raterUsername){
        Optional<User> op = userRepository.findById(id);

        if(op.isEmpty())
            throw new NullPointerException();

        User user = op.get();

        if(isRatedBy(user, raterUsername)){
            user.setRating(user.getRating() - 1);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isRated(Long id, String raterUsername){
        Optional<User> op = userRepository.findById(id);

        if(op.isEmpty())
            throw new NullPointerException();

        User user = op.get();
        return isRatedBy(user, raterUsername);
    }

    @Override
    public ArrayList<JobApplicationDTO> getJobApplications(String username){
        User user = userRepository.findUserByUsername(username);
        return (ArrayList<JobApplicationDTO>) user.getApplications().stream().map(JobApplicationDTO::new).collect(Collectors.toList());
    }

    @Override
    public ArrayList<ProjectMinDTO> getProjects(String username) {
        return (ArrayList<ProjectMinDTO>) userRepository.findUserByUsername(username).getProjectMember().stream().map(ProjectMember::getProject).map(ProjectMinDTO::new).collect(Collectors.toList());
    }

    @Override
    public ArrayList<ProjectInvitationMinDTO> getInvitations(String username) {
        return (ArrayList<ProjectInvitationMinDTO>) userRepository.findUserByUsername(username).getProjectInvitations().stream().map(ProjectInvitationMinDTO::new).collect(Collectors.toList());
    }
}
