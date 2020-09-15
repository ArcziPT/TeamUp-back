package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.JobPosting;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends CrudRepository<JobPosting, Long>, JpaSpecificationExecutor<JobPosting> {
}
