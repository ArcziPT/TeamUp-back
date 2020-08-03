package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.ProjectMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberRepository extends CrudRepository<ProjectMember, Long> {
}
