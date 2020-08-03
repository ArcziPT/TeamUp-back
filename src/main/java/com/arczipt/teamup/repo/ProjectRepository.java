package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    Project findProjectById(Long id);

    Project findProjectByName(String name);

    @Query("from Project p where p.name like pattern")
    ArrayList<Project> findWithNameLike(String pattern);
}
