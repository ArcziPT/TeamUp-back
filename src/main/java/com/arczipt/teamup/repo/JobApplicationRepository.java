package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.JobApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends CrudRepository<JobApplication, Long> {

}
