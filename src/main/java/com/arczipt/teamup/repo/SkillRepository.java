package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {
    Skill findByName(String name);
}
