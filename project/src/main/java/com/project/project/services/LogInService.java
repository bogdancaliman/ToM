package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.PasswordMatchException;
import com.project.project.exceptions.SystemException;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.repositories.AccountRepository;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class LogInService {

    AccountRepository accountRepository;

    @Autowired
    public LogInService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account searchForUser(String username) throws UserNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (!accountOptional.isPresent())
            throw new UserNotFoundException();
        return accountOptional.get();
    }


    public void checkCredentials(Account account, String password) throws SystemException, PasswordMatchException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String aux = password + account.getSalt();
            if (!account.getPassword().equals(DatatypeConverter.printHexBinary(md.digest(aux.getBytes(StandardCharsets.UTF_8)))))
                throw new PasswordMatchException();
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException();
        }
    }

}