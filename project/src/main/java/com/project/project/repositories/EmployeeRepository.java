package com.project.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}