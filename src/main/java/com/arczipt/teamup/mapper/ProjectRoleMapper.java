package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.RoleDTO;
import com.arczipt.teamup.model.Department;
import com.arczipt.teamup.model.ProjectRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DepartmentMapper.class})
public interface ProjectRoleMapper {

    ProjectRoleMapper INSTANCE = Mappers.getMapper(ProjectRoleMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "departments", source = "departments")
    RoleDTO mapToRoleDTO(ProjectRole role);
}
