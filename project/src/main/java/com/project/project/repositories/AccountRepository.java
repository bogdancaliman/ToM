package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Account;
import com.project.project.models.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmployee_Id(String id);
    List <Account> findAllByTeamLeader(Account tl);
    List <Account> findAllByTeamLeaderIsNullAndEmployee_Department(Department department);
}