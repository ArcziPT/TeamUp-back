package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.ProjectInvitationDTO;
import com.arczipt.teamup.dto.ProjectInvitationMinDTO;

import java.util.ArrayList;

public interface ProjectInvitationService {
    ProjectInvitationDTO getInvitation(Long id);

    void accept(Long invitationId);

    void decline(Long invitationId);
}
