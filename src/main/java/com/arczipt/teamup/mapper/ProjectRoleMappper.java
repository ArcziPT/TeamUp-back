package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.RoleDTO;
import com.arczipt.teamup.model.ProjectRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SkillMapper.class})
public interface ProjectRoleMappper {

    ProjectRoleMappper INSTANCE = Mappers.getMapper(ProjectRoleMappper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "skills", source = "skills")
    RoleDTO mapToRoleDTO(ProjectRole role);
}
