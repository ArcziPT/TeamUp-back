package com.arczipt.teamup.service;

import com.arczipt.teamup.model.ApplicationStatus;
import com.arczipt.teamup.dto.JobApplicationDTO;
import com.arczipt.teamup.model.InvitationStatus;
import com.arczipt.teamup.model.ProjectInvitation;
import com.arczipt.teamup.repo.JobApplicationRepository;
import com.arczipt.teamup.repo.ProjectInvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private JobApplicationRepository jobApplicationRepository;
    private ProjectInvitationRepository projectInvitationRepository;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository,
                                     ProjectInvitationRepository projectInvitationRepository){
        this.jobApplicationRepository = jobApplicationRepository;
        this.projectInvitationRepository = projectInvitationRepository;
    }

    @Override
    public JobApplicationDTO findById(Long id) {
        return jobApplicationRepository.findById(id).map(JobApplicationDTO::new).orElseGet(() -> null);
    }

    /**
     * Accept application and send invitation to user.
     *
     * @param appId - app's id
     */
    @Override
    public void accept(Long appId) {
        jobApplicationRepository.findById(appId).ifPresent(app -> {
            ProjectInvitation invitation = new ProjectInvitation();
            invitation.setUser(app.getApplicant());
            invitation.setRole(app.getJobPosting().getRole());
            invitation.setProject(app.getJobPosting().getProject());
            invitation.setStatus(InvitationStatus.ACCEPTED);

            projectInvitationRepository.save(invitation);

            app.setStatus(ApplicationStatus.ACCEPTED);
        });
    }

    @Override
    public void decline(Long appId) {
        jobApplicationRepository.findById(appId).ifPresent(app -> {
            app.setStatus(ApplicationStatus.DECLINED);
        });
    }

    @Override
    public void waitlist(Long appId) {
        jobApplicationRepository.findById(appId).ifPresent(app -> {
            app.setStatus(ApplicationStatus.WAIT_LIST);
        });
    }
}
