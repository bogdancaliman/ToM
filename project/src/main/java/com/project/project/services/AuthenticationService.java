package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.models.Employee;
import com.project.project.repositories.AccountRepository;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getSaltOfUser(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent())
            return accountOptional.get().getSalt();
        else
            return "";
    }

    public boolean amITeamLeader(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional.filter(account -> !account.getMembers().isEmpty()).isPresent();
    }

    public Employee getMyEmployeeData(String username) throws UserNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent())
            return accountOptional.get().getEmployee();
        else
            throw new UserNotFoundException("The user was not found!");
    }
}