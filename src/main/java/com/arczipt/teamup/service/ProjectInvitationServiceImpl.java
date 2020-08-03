package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.ProjectInvitationDTO;
import com.arczipt.teamup.model.InvitationStatus;
import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.repo.ProjectInvitationRepository;
import com.arczipt.teamup.repo.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectInvitationServiceImpl implements ProjectInvitationService {

    private ProjectInvitationRepository projectInvitationRepository;
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    public ProjectInvitationServiceImpl(ProjectInvitationRepository projectInvitationRepository,
                                        ProjectMemberRepository projectMemberRepository){
        this.projectInvitationRepository = projectInvitationRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Override
    public ProjectInvitationDTO getInvitation(Long id) {
        return projectInvitationRepository.findById(id).map(ProjectInvitationDTO::new).orElseGet(null);
    }

    @Override
    public void accept(Long invitationId) {
        projectInvitationRepository.findById(invitationId).ifPresent(inv -> {
            inv.setStatus(InvitationStatus.ACCEPTED);

            ProjectMember member = new ProjectMember();
            member.setUser(inv.getUser());
            member.setRole(inv.getRole());
            member.setProject(inv.getProject());

            projectMemberRepository.save(member);
        });
    }

    @Override
    public void decline(Long invitationId) {
        projectInvitationRepository.findById(invitationId).ifPresent(inv -> {
            inv.setStatus(InvitationStatus.DECLINED);
        });
    }
}
