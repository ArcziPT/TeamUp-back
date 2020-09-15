package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Project findProjectById(Long id);

    Project findProjectByName(String name);

    @Query("from Project p where p.name like :pattern")
    Page<Project> findWithNameLike(@Param("pattern") String pattern, Pageable pageable);
}
