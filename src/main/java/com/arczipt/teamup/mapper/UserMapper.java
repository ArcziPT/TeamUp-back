package com.arczipt.teamup.mapper;

import com.arczipt.teamup.controller.UserController;
import com.arczipt.teamup.dto.NameAndLinkDTO;
import com.arczipt.teamup.dto.UserDTO;
import com.arczipt.teamup.dto.UserMinDTO;
import com.arczipt.teamup.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.util.List;

@Mapper(uses = {SkillMapper.class, ProjectMemberMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "projects", source = "projectMember", qualifiedByName = "listProjectMemberToProjectNameAndLink")
    UserMinDTO mapToUserMinDTO(User user);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "urls", source = "urls")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "projects", source = "projectMember", qualifiedByName = "listProjectMemberToProjectNameAndLink")
    UserDTO mapToUserDTO(User user);

    List<NameAndLinkDTO> mapToNameAndLinkDTO(List<User> users);

    default NameAndLinkDTO mapToNameAndLinkDTO(User user){
        NameAndLinkDTO dto = new NameAndLinkDTO();
        dto.setName(user.getUsername());
        dto.add(linkTo(methodOn(UserController.class).getProfile(user.getId())).withSelfRel());
        return dto;
    }
}
