package com.arczipt.teamup.service;

import com.arczipt.teamup.mapper.*;
import com.arczipt.teamup.model.*;
import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{

    private ProjectRepository projectRepository;
    private ProjectRoleRepository projectRoleRepository;
    private UserRepository userRepository;
    private ProjectInvitationRepository projectInvitationRepository;
    private JobPostingRepository jobPostingRepository;
    private DepartmentRepository departmentRepository;
    private JobApplicationRepository jobApplicationRepository;
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectRoleRepository projectRoleRepository,
                              UserRepository userRepository,
                              ProjectInvitationRepository projectInvitationRepository,
                              JobPostingRepository jobPostingRepository,
                              DepartmentRepository departmentRepository,
                              JobApplicationRepository jobApplicationRepository,
                              ProjectMemberRepository projectMemberRepository){
        this.projectRepository = projectRepository;
        this.projectRoleRepository = projectRoleRepository;
        this.userRepository = userRepository;
        this.projectInvitationRepository = projectInvitationRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.departmentRepository = departmentRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    @Override
    public ProjectDTO findById(Long id) {
        Optional<Project> op = projectRepository.findById(id);

        if(op.isEmpty())
            return null;

        return ProjectMapper.INSTANCE.mapToProjectDTO(op.get());
    }

    @Transactional
    @Override
    public ProjectDTO findByName(String name) {
        return ProjectMapper.INSTANCE.mapToProjectDTO(projectRepository.findProjectByName(name));
    }

    /**
     * Create new project.
     *
     * @param projectDTO
     * @param username - creators's username
     * @return true - no errors, false - otherwise
     */
    @Transactional
    @Override
    public boolean createProject(ProjectCreateDTO projectDTO, String username) {
        User owner = userRepository.findUserByUsername(username);

        if(owner == null)
            return false;

        //create project
        Project project = new Project();
        project.setMembers(new ArrayList<>());
        project.setDescription(projectDTO.getDescription());
        project.setUrls(projectDTO.getUrls());
        project.setBriefDescription(projectDTO.getBriefDescription());
        project.setName(projectDTO.getName());
        project.setJobPostings(new ArrayList<>());
        project.setInvitations(new ArrayList<>());
        project.setDepartments(new ArrayList<>());
        projectRepository.save(project);

        //create owner's role
        ProjectRole role = new ProjectRole();
        role.setName(projectDTO.getRole().getName());
        role.setDescription(projectDTO.getRole().getDescription());

        //create project's departments
        List<Department> departments = projectDTO.getRole().getDepartments().stream().map(name -> {
            Department dep = new Department();
            dep.setName(name);
            dep.setProject(project);
            departmentRepository.save(dep);

            return dep;
        }).collect(Collectors.toList());
        role.setDepartments(departments);
        projectRoleRepository.save(role);

        //add creator
        ProjectMember pm = new ProjectMember();
        pm.setUser(owner);
        pm.setAdmin(true);
        pm.setProject(project);
        pm.setRole(role);
        projectMemberRepository.save(pm);

        return true;
    }

    /**
     * Find projects with name matching pattern.
     *
     * @param pattern
     * @param pageable
     * @return list of minimal projects' descriptions.
     */
    @Transactional
    @Override
    public SearchResult<ProjectMinDTO> findWithNameLike(String pattern, Pageable pageable) {
        pattern += "%"; //create SQL regex to find every name matching pattern with varying ending
        Page<Project> page = projectRepository.findWithNameLike(pattern, pageable);

        SearchResult<ProjectMinDTO> result = new SearchResult<>();
        result.setResult(page.stream().map(ProjectMapper.INSTANCE::mapToProjectMinDTO).collect(Collectors.toList()));
        result.setTotalPages(page.getTotalPages());

        return result;
    }

    @Transactional
    @Override
    public ArrayList<ProjectMemberDTO> getMembers(Long id) {
        Optional<Project> op =  projectRepository.findById(id);

        return (ArrayList<ProjectMemberDTO>) op.map(project -> project.getMembers().stream().map(ProjectMemberMapper.INSTANCE::mapToProjectMemberDTO).collect(Collectors.toList())).orElse(null);
    }

    /**
     * Send invitation to user.
     *
     * @param projectInvitation
     * @return
     */
    @Transactional
    @Override
    public boolean sendInvitation(ProjectInvitationCreateDTO projectInvitation) {
        //check if user is not already a member of the project
        Optional<ProjectMember> op = userRepository.findUserByUsername(projectInvitation.getUsername()).getProjectMember().stream().filter(
                pm -> pm.getProject().getName().equals(projectInvitation.getProjectName())
        ).findAny();

        if(op.isEmpty()){

            //create new role
            ProjectRole role = new ProjectRole();
            role.setName(projectInvitation.getRole().getName());
            role.setDescription(projectInvitation.getRole().getDescription());

            List<Department> departments = projectInvitation.getRole().getDepartments().stream().map(departmentRepository::findByName).collect(Collectors.toList());
            role.setDepartments(departments);
            projectRoleRepository.save(role);

            //create invitation
            ProjectInvitation invitation = new ProjectInvitation();
            invitation.setStatus(InvitationStatus.WAITING);
            invitation.setProject(projectRepository.findProjectByName(projectInvitation.getProjectName()));
            invitation.setRole(role);
            invitation.setUser(userRepository.findUserByUsername(projectInvitation.getUsername()));

            projectInvitationRepository.save(invitation);
            return true;
        }else
            return false;
    }

    @Transactional
    @Override
    public ArrayList<ProjectInvitationDTO> getInvitations(Long id) {
        Optional<Project> op = projectRepository.findById(id);

        return op.map(project -> (ArrayList<ProjectInvitationDTO>) project.getInvitations().stream().map(ProjectInvitationMapper.INSTANCE::mapToProjectInvitationDTO).collect(Collectors.toList())).orElse(null);
    }

    @Transactional
    @Override
    public boolean hasApplied(String username, Long projectId, Long postingId) {
        User user = userRepository.findUserByUsername(username);

        //ignore not existing users
        if(user == null)
            return false;

        Optional<JobApplication> op = user.getApplications().stream().filter(app -> app.getJobPosting().getId().equals(postingId)).findAny();
        return op.isPresent();
    }

    /**
     * Add new job application to posting.
     *
     * @param username
     * @param projectId
     * @param postingId
     * @return
     */
    @Transactional
    @Override
    public boolean apply(String username, Long projectId, Long postingId) {
        AtomicBoolean ret = new AtomicBoolean(false);

        //check if posting exist
        jobPostingRepository.findById(postingId).filter(jobPosting -> jobPosting.getProject().getId().equals(projectId)).ifPresent(jobPosting -> {
            if(!hasApplied(username, projectId, postingId)){

                //check if user is member of a project
                if(jobPosting.getProject().getMembers().stream().anyMatch(pm -> pm.getUser().getUsername().equals(username)))
                    return;

                ret.set(true);

                JobApplication jobApplication = new JobApplication();
                jobApplication.setJobPosting(jobPosting);
                jobApplication.setStatus(ApplicationStatus.WAITING);
                jobApplication.setApplicant(userRepository.findUserByUsername(username));
                jobApplicationRepository.save(jobApplication);
            }
        });

        return ret.get();
    }

    @Transactional
    @Override
    public ArrayList<JobPostingDTO> getJobPostings(Long id, String username) {
        return (ArrayList<JobPostingDTO>) projectRepository.findById(id).map(project -> project.getJobPostings().stream().map(jobPosting -> {
            return JobPostingMapper.INSTANCE.mapToJobPostingDTO(jobPosting, hasApplied(username, id, jobPosting.getId()));
        }).collect(Collectors.toList())).orElseGet(null);
    }

    @Override
    @Transactional
    public boolean addJobPosting(Long id, JobPostingCreateDTO jobPosting) {
        ArrayList<Department> departments = (ArrayList<Department>) jobPosting.getRole().getDepartments().stream().map(departmentRepository::findByName).collect(Collectors.toList());

        ProjectRole role = new ProjectRole();
        role.setName(jobPosting.getRole().getName());
        role.setDescription(jobPosting.getRole().getDescription());
        role.setDepartments(departments);
        projectRoleRepository.save(role);

        JobPosting posting = new JobPosting();
        posting.setApplications(new ArrayList<>());
        posting.setRole(role);
        posting.setTitle(jobPosting.getTitle());
        projectRepository.findById(id).ifPresent(posting::setProject);

        jobPostingRepository.save(posting);
        return true;
    }

    @Transactional
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

    @Transactional
    @Override
    public boolean isMember(String username, Long id) {
        return projectRepository.findById(id).map(project -> project.getMembers().stream().anyMatch(projectMember -> projectMember.getUser().getUsername().equals(username))).orElse(false);
    }

    @Transactional
    @Override
    public boolean edit(Long id, ProjectDTO projectDTO) {
        Optional<Project> op = projectRepository.findById(id);

        if(op.isEmpty())
            return false;

        Project project = op.get();
        project.setName(projectDTO.getName());
        project.setBriefDescription(projectDTO.getBriefDescription());
        project.setDescription(projectDTO.getDescription());
        project.setUrls(projectDTO.getUrls());
        projectRepository.save(project);

        return true;
    }

    @Transactional
    @Override
    public boolean removeMember(Long id, String username) {
        Optional<Project> op = projectRepository.findById(id);

        if(op.isEmpty())
            return false;

        Project project = op.get();
        AtomicBoolean found = new AtomicBoolean(false);
        project.setMembers(
                project.getMembers().stream().filter(member -> {
                    if(!member.getAdmin() && member.getUser().getUsername().equals(username)){
                        found.set(true);
                        return false;
                    }
                    return true;
                }).collect(Collectors.toList())
        );

        return found.get();
    }

    @Transactional
    @Override
    public boolean makeAdmin(Long id, String username) {
        Optional<Project> op = projectRepository.findById(id);

        if(op.isEmpty())
            return false;

        Project project = op.get();
        Optional<ProjectMember> opm = project.getMembers().stream().filter(member -> member.getUser().getUsername().equals(username)).findAny();

        if(opm.isEmpty())
            return false;

        ProjectMember pm = opm.get();
        pm.setAdmin(true);
        projectMemberRepository.save(pm);
        return true;
    }

    @Transactional
    @Override
    public ArrayList<IdAndNameDTO> getDepartments(Long id) {
        Optional<Project> op = projectRepository.findById(id);

        if(op.isEmpty())
            throw new EntityNotFoundException();

        return (ArrayList<IdAndNameDTO>) op.get().getDepartments().stream().map(DepartmentMapper.INSTANCE::mapToIdAndName).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean addDepartment(Long id, String departmentName) {
        Optional<Project> op = projectRepository.findById(id);

        if(op.isEmpty())
            return false;

        Department dep = new Department();
        dep.setProject(op.get());
        dep.setName(departmentName);
        departmentRepository.save(dep);

        return true;
    }
}
