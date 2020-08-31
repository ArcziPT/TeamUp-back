package com.arczipt.teamup.repository;

import com.arczipt.teamup.dto.UserMinDTO;
import com.arczipt.teamup.model.Skill;
import com.arczipt.teamup.model.User;
import com.arczipt.teamup.repo.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Test only custom queries used in user repository.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired private TestEntityManager entityManager;
    @Autowired private UserRepository userRepository;

    @Test
    public void componentsAreNotNull(){
        assertThat(entityManager).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void findWithUsernameLikeTest(){
        User u1 = new User();
        u1.setUsername("john123");
        User u2 = new User();
        u2.setUsername("john456");
        User u3 = new User();
        u3.setUsername("michael123");

        entityManager.persist(u1);
        entityManager.persist(u2);
        entityManager.persist(u3);
        entityManager.flush();

        Page<User> users = userRepository.findUsersWithUsernameLike("john%", PageRequest.of(0, 3));
        assertThat((int) users.get().count()).isEqualTo(2);

        users = userRepository.findUsersWithUsernameLike("%123", PageRequest.of(0, 3));
        assertThat((int) users.get().count()).isEqualTo(2);
    }

    @Test
    public void findBySkillName(){
        Skill s1 = new Skill();
        Skill s2 = new Skill();

        s1.setName("jumping");
        s2.setName("running");

        User u1 = new User();
        u1.setUsername("john");
        u1.setSkills(Collections.singletonList(s1));
        User u2 = new User();
        u2.setUsername("michael");
        u2.setSkills(Collections.singletonList(s2));
        User u3 = new User();
        u3.setUsername("nicolas");
        u3.setSkills(Collections.singletonList(s2));

        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(u1);
        entityManager.persist(u2);
        entityManager.persist(u3);
        entityManager.flush();

        Page<User> users = userRepository.findUsersBySkillName("jumping", PageRequest.of(0, 3));
        assertThat(users.get().count()).isEqualTo(1);
        assertThat(users.stream().map(User::getUsername)).contains(u1.getUsername());

        users = userRepository.findUsersBySkillName("running", PageRequest.of(0, 3));
        assertThat(users.get().count()).isEqualTo(2);


        users = userRepository.findUsersBySkillName("skill", PageRequest.of(0, 3));
        assertThat(users.get().count()).isEqualTo(0);
    }
}
