package com.arczipt.teamup.service;

import com.arczipt.teamup.dto.UserDTO;
import com.arczipt.teamup.dto.UserMinDTO;
import com.arczipt.teamup.model.User;
import com.arczipt.teamup.repo.UserRepository;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {UserServiceImpl.class, UserRepository.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    private User getDummyUser(int id, String username){
        return new User((long) id, username, "hash", "firstname", "surname", "e@mail.com", new ArrayList<>(), new ArrayList<>(), "desc", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void whenIsRated_shouldReturnTFalse(){
        User user = getDummyUser(1, "myUser1");
        User rater = getDummyUser(2, "raterUser");

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean isRated = userService.isRated(user.getId(), rater.getUsername());
        assertThat(isRated).isEqualTo(false);
    }

    @Test
    public void whenIsRated_shouldReturnTrue(){
        User user = getDummyUser(1, "myUser1");
        User rater = getDummyUser(2, "raterUser");

        user.getRatedBy().add(rater);
        rater.getRatedUsers().add(user);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean isRated = userService.isRated(user.getId(), rater.getUsername());
        assertThat(isRated).isEqualTo(true);
    }

    @Test
    public void whenRateUser_shouldReturnTrue(){
        User user = getDummyUser(1, "myUser1");
        User rater = getDummyUser(2, "raterUser");

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean isRated = userService.rateUser(user.getId(), rater.getUsername());
        assertThat(isRated).isEqualTo(true);
    }

    @Test
    public void whenRateUser_shouldReturnFalse(){
        User user = getDummyUser(1, "myUser1");
        User rater = getDummyUser(2, "raterUser");

        user.getRatedBy().add(rater);
        rater.getRatedUsers().add(user);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean isRated = userService.rateUser(user.getId(), rater.getUsername());
        assertThat(isRated).isEqualTo(false);
    }

    @Test
    public void whenUnrateUser_shouldReturnTrue(){
        User user = getDummyUser(1, "myUser1");
        User rater = getDummyUser(2, "raterUser");

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean isRated = userService.unrateUser(user.getId(), rater.getUsername());
        assertThat(isRated).isEqualTo(false);
    }

    @Test
    public void whenUnrateUser_shouldReturnFalse(){
        User user = getDummyUser(1, "myUser1");
        User rater = getDummyUser(2, "raterUser");

        user.getRatedBy().add(rater);
        rater.getRatedUsers().add(user);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean isRated = userService.unrateUser(user.getId(), rater.getUsername());
        assertThat(isRated).isEqualTo(true);
    }

    @Test
    public void shouldReturnUserById(){
        User user = new User((long) 1, "username", "hash", "firstname", "surname", "e@mail.com", new ArrayList<>(), new ArrayList<>(), "desc", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.findById(user.getId());
        assertThat(userDTO.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void shouldReturnUserByUsername() {
        User user = new User((long) 1, "username", "hash", "firstname", "surname", "e@mail.com", new ArrayList<>(), new ArrayList<>(), "desc", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        UserDTO userDTO = userService.findByUsername(user.getUsername());
        assertThat(userDTO.getUsername()).isEqualTo(user.getUsername());
    }
}
