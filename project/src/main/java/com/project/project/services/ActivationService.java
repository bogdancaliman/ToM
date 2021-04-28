package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.models.Account;
import com.project.project.repositories.AccountRepository;

import java.util.Optional;

@Service
public class ActivationService {

    private final AccountRepository accountRepository;

    @Autowired
    public ActivationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void activateMyAccount(String userId) {
        Optional<Account> accountOptional = accountRepository.findById(userId);
        if(accountOptional.isPresent()) {
            Account account =  accountOptional.get();
            account.setActivated(true);
            accountRepository.save(account);
        }
    }

    public boolean isUserActivated(String userId) {
        Optional<Account> accountOptional = accountRepository.findById(userId);
        return accountOptional.map(Account::isActivated).orElse(false);
    }
}