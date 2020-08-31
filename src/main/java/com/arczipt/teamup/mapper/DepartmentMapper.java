package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.IdAndNameDTO;
import com.arczipt.teamup.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    List<String> mapToName(List<Department> departments);

    default String mapToName(Department department){
        return department.getName();
    }

    List<IdAndNameDTO> mapToIdAndName(List<Department> departments);

    default IdAndNameDTO mapToIdAndName(Department department){
        IdAndNameDTO dto = new IdAndNameDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());

        return dto;
    }
}
