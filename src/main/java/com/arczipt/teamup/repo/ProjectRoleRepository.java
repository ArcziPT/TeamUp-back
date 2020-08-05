package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.ProjectRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRoleRepository extends CrudRepository<ProjectRole, Long> {
    ProjectRole findByName(String role);
}
