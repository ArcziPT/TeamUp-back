package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.*;
import com.arczipt.teamup.mapper.ProjectInvitationMapper;
import com.arczipt.teamup.mapper.ProjectMapper;
import com.arczipt.teamup.mapper.ProjectRoleMapper;
import com.arczipt.teamup.mapper.UserMapper;
import com.arczipt.teamup.model.*;
import com.arczipt.teamup.repo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProjectServiceImpl.class})
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectRoleRepository projectRoleRepository;
    @MockBean
    private SkillRepository skillRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ProjectInvitationRepository projectInvitationRepository;
    @MockBean
    private JobPostingRepository jobPostingRepository;

    @Test
    public void whenSendInvitation_ShouldCreateProjectInvitation(){
        User user = new User((long) 1, "my_user", "hash", new ArrayList<>(), new ArrayList<>(), "bDesc", "desc", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Project project = new Project((long) 1, "my_project", new ArrayList<>(), new ArrayList<>(), "desc", "desc", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ProjectRole role = new ProjectRole((long) 1, "my_role", "role desc", new ArrayList<>());
        RoleDTO roleDTO = ProjectRoleMapper.INSTANCE.mapToRoleDTO(role);
        ProjectInvitationCreateDTO projectInvitation = new ProjectInvitationCreateDTO(project.getName(), user.getUsername(), roleDTO);

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(projectRepository.findProjectByName(project.getName())).thenReturn(project);
        Mockito.when(projectInvitationRepository.save(Mockito.any())).thenAnswer(new Answer<ProjectInvitation>() {
            @Override
            public ProjectInvitation answer(InvocationOnMock invocationOnMock) throws Throwable {
                ProjectInvitation saved = (ProjectInvitation) invocationOnMock.getArguments()[0];

                assertThat(ProjectInvitationMapper.INSTANCE.mapToProjectInvitationDTO(saved)).isEqualToComparingFieldByField(projectInvitation); //actual test
                return saved;
            }
        });

        //call
        projectService.sendInvitation(projectInvitation);

        Mockito.verify(projectInvitationRepository, Mockito.times(1)).save(Mockito.any()); //assert that actual test will be run
    }

    @Test
    public void whenAddJobPosting_ShouldCreateJobPosting(){
        Project project = new Project((long) 1, "my_project", new ArrayList<>(), new ArrayList<>(), "desc", "desc", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ProjectRole role = new ProjectRole((long) 1, "my_role", "role desc", new ArrayList<>());
        JobPostingCreateDTO postingDTO = new JobPostingCreateDTO("team position", ProjectRoleMapper.INSTANCE.mapToRoleDTO(role));

        Mockito.when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        Mockito.when(jobPostingRepository.save(Mockito.any())).thenAnswer(new Answer<JobPosting>() {
            @Override
            public JobPosting answer(InvocationOnMock invocationOnMock) throws Throwable {
                JobPosting jobPosting = (JobPosting) invocationOnMock.getArguments()[0];
                JobPostingCreateDTO saved = new JobPostingCreateDTO(jobPosting.getTitle(), ProjectRoleMapper.INSTANCE.mapToRoleDTO(jobPosting.getRole()));

                assertThat(saved).isEqualToComparingFieldByField(postingDTO);
                return jobPosting;
            }
        });

        projectService.addJobPosting(project.getId(), postingDTO);

        Mockito.verify(jobPostingRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void whenIsAdmin_ShouldReturnTrue() {
        User user = new User((long) 1, "my_user", "hash", new ArrayList<>(), new ArrayList<>(), "bDesc", "desc", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Project project = new Project((long) 1, "my_project", new ArrayList<>(), new ArrayList<>(), "desc", "desc", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ProjectMember member = new ProjectMember((long) 1, user, null, project, true);

        project.getMembers().add(member);
        user.getProjectMember().add(member);

        Mockito.when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        boolean isAdmin = projectService.isAdmin(project.getId(), user.getUsername());
        assertThat(isAdmin).isEqualTo(true);
    }
}
