package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Employee;
import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findAllByDepartment_Id(int department);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByAccount_Username(String username);
}