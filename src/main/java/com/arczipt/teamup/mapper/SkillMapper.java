package com.arczipt.teamup.mapper;

import com.arczipt.teamup.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SkillMapper {

    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

    List<String> mapToString(List<Skill> skills);

    default String mapToString(Skill skill){
        return skill.getName();
    }
}
