package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Department findByName(String name);
}
