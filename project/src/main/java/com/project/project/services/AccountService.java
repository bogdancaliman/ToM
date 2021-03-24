package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.LogInException;
import com.project.project.exceptions.PasswordMatchException;
import com.project.project.exceptions.SystemException;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.repositories.AccountRepository;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account checkCredentials(String username, String password) throws LogInException, SystemException {
        Account acc = accountRepository.findByUsername(username);
        if (acc != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String aux = password + acc.getSalt();
                if (acc.getPassword().equals(DatatypeConverter.printHexBinary(md.digest(aux.getBytes(StandardCharsets.UTF_8))))) {
                    return acc;
                } else {
                    throw new PasswordMatchException();
                }
            } catch (NoSuchAlgorithmException e) {
                throw new SystemException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }
 }