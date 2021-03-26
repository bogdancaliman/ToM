package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.LogInException;
import com.project.project.exceptions.PasswordMatchException;
import com.project.project.exceptions.SystemException;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.repositories.AccountRepository;
import com.project.project.dtos.EmailData;
import com.project.project.dtos.ResetPasswordEmail;
import com.project.project.models.ResetPassReq;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import com.project.project.repositories.ResetPassReqRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResetPassReqRepository resetPassReqRepository;

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
    public void sendResetEmail(String username, HttpServletRequest request) throws UserNotFoundException{
        Account acc = accountRepository.findByUsername(username);
        if(acc == null) {
            throw new UserNotFoundException();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, 1);

            ResetPassReq req = new ResetPassReq();
            req.setAccount_req(acc);
            req.setExpirationDate(calendar.getTime());
            req.setToken(UUID.randomUUID().toString());
            resetPassReqRepository.save(req);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            ResetPasswordEmail data = new ResetPasswordEmail( appUrl + "/reset?token=" + req.getToken(), username);
            emailService.prepareAndSend(acc.getEmployee().getEmail(), data);
        }
    }
 }