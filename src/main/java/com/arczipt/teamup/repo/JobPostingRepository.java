package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.JobPosting;
import org.springframework.data.repository.CrudRepository;

public interface JobPostingRepository extends CrudRepository<JobPosting, Long> {
}
