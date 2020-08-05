package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.UserMinDTO;
import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.model.Skill;
import com.arczipt.teamup.model.User;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UserMapperTest {

    @Test
    public void mapToUserMinDTOTest(){
        Skill s1 = new Skill((long) 1, "s1");
        Skill s2 = new Skill((long) 2, "s2");
        User user = new User((long) 1, "username", "hash", "name", "surname", "mail", Arrays.asList(s1, s2), new ArrayList<>(), "desc", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        UserMinDTO mapped = UserMapper.INSTANCE.mapToUserMinDTO(user);

        assertThat(mapped.getUsername()).isEqualTo(user.getUsername());
        assertThat(mapped.getRating()).isEqualTo(user.getRating());
        assertThat(mapped.getSkills()).isEqualTo(user.getSkills().stream().map(Skill::getName).collect(Collectors.toList()));
        assertThat(mapped.getProjects()).isEqualTo(user.getProjectMember().stream().map(ProjectMember::getProject).map(Project::getName).collect(Collectors.toList()));
    }

    @Test
    public void mapToUserDTOTest(){
        User user = new User((long) 1, "username", "hash", "name", "surname", "mail", new ArrayList<>(), new ArrayList<>(), "desc", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        UserMinDTO mapped = UserMapper.INSTANCE.mapToUserMinDTO(user);

        assertThat(mapped.getUsername()).isEqualTo(user.getUsername());
        assertThat(mapped.getRating()).isEqualTo(user.getRating());
        assertThat(mapped.getSkills()).isEqualTo(user.getSkills().stream().map(Skill::getName).collect(Collectors.toList()));
        assertThat(mapped.getProjects()).isEqualTo(user.getProjectMember().stream().map(ProjectMember::getProject).map(Project::getName).collect(Collectors.toList()));
    }
}
