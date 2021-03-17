package com.project.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
}