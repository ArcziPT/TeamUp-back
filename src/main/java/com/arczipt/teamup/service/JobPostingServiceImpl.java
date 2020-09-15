package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.mapper.JobApplicationMapper;
import com.arczipt.teamup.mapper.JobPostingMapper;
import com.arczipt.teamup.mapper.UserMapper;
import com.arczipt.teamup.model.*;
import com.arczipt.teamup.repo.JobApplicationRepository;
import com.arczipt.teamup.repo.JobPostingRepository;
import com.arczipt.teamup.repo.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import static com.arczipt.teamup.repo.specifications.JobPostingSpecifications.*;
import static com.arczipt.teamup.repo.specifications.UserSpecifications.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class JobPostingServiceImpl implements JobPostingService{

    private JobPostingRepository jobPostingRepository;
    private JobApplicationRepository jobApplicationRepository;
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    JobPostingServiceImpl(JobPostingRepository jobPostingRepository,
                          JobApplicationRepository jobApplicationRepository,
                          ProjectMemberRepository projectMemberRepository){
        this.jobPostingRepository = jobPostingRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    @Override
    public JobPostingDTO get(Long id, String username) {
        return jobPostingRepository.findById(id).map(jobPosting -> {
            boolean hasApplied = jobPosting.getApplications().stream().map(app -> app.getApplicant().getUsername().equals(username)).findAny().isPresent();
            return JobPostingMapper.INSTANCE.mapToJobPostingDTO(jobPosting, hasApplied);
        }).orElse(null);
    }

    @Override
    public SearchResult<JobPostingMinDTO> search(String title, String project, String roleName, ArrayList<String> departments, Pageable pageable) {
        title += '%';
        project += '%';
        roleName += '%';
        Page<JobPosting> page = jobPostingRepository.findAll(withTitleLike(title).and(withProjectLike(project)).and(withRoleNameLike(roleName)).and(withDepartments(departments)), pageable);

        SearchResult<JobPostingMinDTO> result = new SearchResult<>();
        result.setTotalPages(page.getTotalPages());
        result.setResult(page.map(JobPostingMapper.INSTANCE::mapToJobPostingMinDTO).toList());

        return result;
    }

    @Transactional
    @Override
    public SearchResult<JobApplicationDTO> getApplications(Long id, Pageable pageable) {
        Page<JobApplication> page = jobApplicationRepository.findAppsByPostingId(id, pageable);

        return new SearchResult<>(page.get().map(JobApplicationMapper.INSTANCE::mapToJobApplicationDTO).collect(Collectors.toList()), page.getTotalPages());
    }

    /**
     * Updates application status and if necessary adds new project member.
     *
     * @param postingId
     * @param appId
     * @param accepted - true if job application is accepted else false
     * @return true - no errors, false - otherwise
     */
    @Transactional
    @Override
    public boolean updateApplicationStatus(Long postingId, Long appId, Boolean accepted) {
        AtomicBoolean ret = new AtomicBoolean(false);

        //find job application
        jobApplicationRepository.findById(appId).ifPresent(app -> {

            //error if it is already accepted/declinde
            if (app.getStatus() != ApplicationStatus.WAITING)
                return;

            ret.set(true);

            if(accepted){
                //update status and add new project member
                app.setStatus(ApplicationStatus.ACCEPTED);

                ProjectMember pm = new ProjectMember();
                pm.setRole(app.getJobPosting().getRole());
                pm.setProject(app.getJobPosting().getProject());
                pm.setAdmin(false);
                pm.setUser(app.getApplicant());
                projectMemberRepository.save(pm);
            }
            else
                app.setStatus(ApplicationStatus.DECLINED);

            jobApplicationRepository.save(app);
        });

        return ret.get();
    }
}
