package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.PasswordMatchException;
import com.project.project.exceptions.SystemException;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.repositories.AccountRepository;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AuthService {
    @Autowired
    AccountRepository accountRepository;

    public Account findAccountByUsername(String username) throws UserNotFoundException {
        Account acc = accountRepository.findByUsername(username);
        if (acc == null)
            throw new UserNotFoundException();

        return acc;
    }


    public void checkCredentials(Account acc, String password) throws SystemException, PasswordMatchException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String aux = password + acc.getSalt();
            if (!acc.getPassword().equals(DatatypeConverter.printHexBinary(md.digest(aux.getBytes(StandardCharsets.UTF_8)))))
                throw new PasswordMatchException();
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException();
        }
    }

}