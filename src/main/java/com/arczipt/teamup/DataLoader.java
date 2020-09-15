package com.arczipt.teamup;

import com.arczipt.teamup.mapper.UserMapper;
import com.arczipt.teamup.model.*;
import com.arczipt.teamup.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ProjectRoleRepository projectRoleRepository;
    @Autowired
    private ProjectMemberRepository projectMemberRepository;
    @Autowired
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Skill s1 = new Skill();
        s1.setName("RUNNING");
        Skill s2 = new Skill();
        s2.setName("JUMPING");
        Skill s3 = new Skill();
        s3.setName("RIDING");
        Skill s4 = new Skill();
        s4.setName("SKIING");
        skillRepository.save(s1);
        skillRepository.save(s2);
        skillRepository.save(s3);
        skillRepository.save(s4);


        ProjectRole role = new ProjectRole();
        role.setName("ATHLETE");
        role.setDepartments(new ArrayList<>());
        role.setDescription("new athlete");
        projectRoleRepository.save(role);

        Project project = new Project();
        project.setBriefDescription("project description");
        project.setDescription("longer project description");
        project.setInvitations(new ArrayList<>());
        project.setJobPostings(new ArrayList<>());
        project.setName("PROJECT123");
        project.setUrls(Arrays.asList("github.com/p123", "mojastrona.com"));
        projectRepository.save(project);

        User user = new User();
        user.setRating(123);
        String hash = ((PasswordEncoder) context.getBean("passwordEncoder")).encode("pass");
        user.setHash(hash);
        user.setApplications(new ArrayList<>());
        user.setBriefDescription("user desc");
        user.setProjectInvitations(new ArrayList<>());
        user.setRatedBy(new ArrayList<>());
        user.setRatedUsers(new ArrayList<>());
        user.setSkills(Arrays.asList(s1, s2, s3, s4));
        user.setUrls(Arrays.asList("user123.com", "github.com/user123"));
        user.setUsername("user123");
        userRepository.save(user);

        User user2 = new User();
        user2.setRating(123);
        hash = ((PasswordEncoder) context.getBean("passwordEncoder")).encode("pass");
        user2.setHash(hash);
        user2.setApplications(new ArrayList<>());
        user2.setBriefDescription("user desc");
        user2.setProjectInvitations(new ArrayList<>());
        user2.setRatedBy(new ArrayList<>());
        user2.setRatedUsers(new ArrayList<>());
        user2.setSkills(Arrays.asList(s1, s2, s3, s4));
        user2.setUrls(Arrays.asList("user123.com", "github.com/user123"));
        user2.setUsername("new_user");
        userRepository.save(user2);

        User user3 = new User();
        user3.setRating(123);
        hash = ((PasswordEncoder) context.getBean("passwordEncoder")).encode("pass");
        user3.setHash(hash);
        user3.setApplications(new ArrayList<>());
        user3.setBriefDescription("user desc");
        user3.setProjectInvitations(new ArrayList<>());
        user3.setRatedBy(new ArrayList<>());
        user3.setRatedUsers(new ArrayList<>());
        user3.setSkills(Arrays.asList(s1, s3, s4));
        user3.setUrls(Arrays.asList("user123.com", "github.com/user123"));
        user3.setUsername("new_user2");
        userRepository.save(user3);

        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setRole(role);
        member.setUser(user);
        member.setAdmin(true);
        projectMemberRepository.save(member);

        Department d1 = new Department();
        d1.setName("Dep1");
        d1.setProject(project);
        Department d2 = new Department();
        d2.setName("Dep2");
        d2.setProject(project);
        departmentRepository.save(d1);
        departmentRepository.save(d2);
    }
}
