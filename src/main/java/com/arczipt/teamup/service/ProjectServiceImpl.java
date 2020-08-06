package com.arczipt.teamup.service;

import com.arczipt.teamup.mapper.JobPostingMapper;
import com.arczipt.teamup.mapper.ProjectInvitationMapper;
import com.arczipt.teamup.mapper.ProjectMapper;
import com.arczipt.teamup.mapper.UserMapper;
import com.arczipt.teamup.model.*;
import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{

    private ProjectRepository projectRepository;
    private ProjectRoleRepository projectRoleRepository;
    private SkillRepository skillRepository;
    private UserRepository userRepository;
    private ProjectInvitationRepository projectInvitationRepository;
    private JobPostingRepository jobPostingRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectRoleRepository projectRoleRepository,
                              SkillRepository skillRepository,
                              UserRepository userRepository,
                              ProjectInvitationRepository projectInvitationRepository,
                              JobPostingRepository jobPostingRepository){
        this.projectRepository = projectRepository;
        this.projectRoleRepository = projectRoleRepository;
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
        this.projectInvitationRepository = projectInvitationRepository;
        this.jobPostingRepository = jobPostingRepository;
    }

    @Override
    public ProjectDTO findById(Long id) {
        Optional<Project> op = projectRepository.findById(id);

        if(op.isEmpty())
            return null;

        return ProjectMapper.INSTANCE.mapToProjectDTO(op.get());
    }

    @Override
    public ProjectDTO findByName(String name) {
        return ProjectMapper.INSTANCE.mapToProjectDTO(projectRepository.findProjectByName(name));
    }

    @Override
    public ArrayList<ProjectMinDTO> findWithNameLike(String pattern) {
        return (ArrayList<ProjectMinDTO>) projectRepository.findWithNameLike(pattern).stream().map(ProjectMapper.INSTANCE::mapToProjectMinDTO).collect(Collectors.toList());
    }

    @Override
    public ArrayList<UserMinDTO> getMembers(Long id) {
        Optional<Project> op =  projectRepository.findById(id);

        if(op.isEmpty())
            return null;

        return (ArrayList<UserMinDTO>) op.get().getMembers().stream().map(ProjectMember::getUser).map(UserMapper.INSTANCE::mapToUserMinDTO).collect(Collectors.toList());
    }

    @Override
    public void sendInvitation(ProjectInvitationDTO projectInvitationDTO) {
        ProjectRole role = projectRoleRepository.findByName(projectInvitationDTO.getRole().getName());

        if(role == null){
            //error
        }

        ProjectInvitation invitation = new ProjectInvitation();
        invitation.setStatus(InvitationStatus.WAITING);
        invitation.setProject(projectRepository.findProjectByName(projectInvitationDTO.getProject().getName()));
        invitation.setRole(role);
        invitation.setUser(userRepository.findUserByUsername(projectInvitationDTO.getUser().getName()));

        projectInvitationRepository.save(invitation);
    }

    @Override
    public ArrayList<ProjectInvitationDTO> getInvitations(Long id) {
        Optional<Project> op = projectRepository.findById(id);

        return op.map(project -> (ArrayList<ProjectInvitationDTO>) project.getInvitations().stream().map(ProjectInvitationMapper.INSTANCE::mapToProjectInvitationDTO).collect(Collectors.toList())).orElse(null);
    }

    @Override
    public ArrayList<JobPostingDTO> getJobPostings(Long id) {
        return (ArrayList<JobPostingDTO>) projectRepository.findById(id).map(project -> project.getJobPostings().stream().map(JobPostingMapper.INSTANCE::mapToJobPostingDTO).collect(Collectors.toList())).orElseGet(null);
    }

    @Override
    public void addJobPosting(Long id, JobPostingCreateDTO jobPosting) {
        JobPosting posting = new JobPosting();
        posting.setApplications(new ArrayList<>());
        posting.setRole(projectRoleRepository.findByName(jobPosting.getRole()));
        posting.setTitle(jobPosting.getTitle());

        projectRepository.findById(id).ifPresent(posting::setProject);

        jobPostingRepository.save(posting);
    }

    @Override
    public boolean isAdmin(Long projectId, String username) {
        Optional<Project> op = projectRepository.findById(projectId);

        if(op.isEmpty())
            return false;

        Optional<ProjectMember> opm = op.get().getMembers().stream().filter(projectMember -> projectMember.getUser().getUsername().equals(username)).findAny();

        if(opm.isEmpty())
            return false;
        else
            return opm.map(ProjectMember::getAdmin).get();
    }
}
