package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.project.project.dtos.TOMUserDetails;
import com.project.project.models.Account;
import com.project.project.repositories.AccountRepository;

import java.util.Optional;

@Service
public class TOMUserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public TOMUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(s);
        if(accountOptional.isPresent())
            return new TOMUserDetails(accountOptional.get());
        else
            throw new UsernameNotFoundException("User not found!");
    }
}