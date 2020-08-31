package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends PagingAndSortingRepository<JobApplication, Long> {
    @Query("from JobApplication app where app.jobPosting.id = :id")
    Page<JobApplication> findAppsByPostingId(Long id, Pageable pageable);
}
