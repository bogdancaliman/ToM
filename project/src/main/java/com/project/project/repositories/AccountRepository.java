package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Account;
import com.project.project.models.Department;
import com.project.project.models.Employee;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);
    Account findById(int id);
    Account findByEmployee_Id(int id);
    List <Account> findAllByTl(Account tl);
    List <Account> findAllByTlIsNullAndEmployee_Department(Department department);
}