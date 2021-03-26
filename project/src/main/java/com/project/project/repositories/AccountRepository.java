package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);
}