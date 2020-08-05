package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.UserDTO;
import com.arczipt.teamup.dto.UserMinDTO;
import com.arczipt.teamup.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.jmx.export.annotation.ManagedAttribute;

@Mapper(uses = {SkillMapper.class, ProjectMemberMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "projects", source = "projectMember", qualifiedByName = "listProjectMemberToProjectName")
    UserMinDTO mapToUserMinDTO(User user);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "urls", source = "urls")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "projects", source = "projectMember", qualifiedByName = "listProjectMemberToProjectName")
    UserDTO mapToUserDTO(User user);
}
