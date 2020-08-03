package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.ProjectInvitation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectInvitationRepository extends CrudRepository<ProjectInvitation, Long> {

}
