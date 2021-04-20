package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import com.project.project.dtos.ResetPasswordEmail;
import com.project.project.exceptions.*;
import com.project.project.models.Account;
import com.project.project.models.ResetPasswordRequest;
import com.project.project.repositories.AccountRepository;
import com.project.project.repositories.ResetPasswordRequestRepository;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordService {

    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final ResetPasswordRequestRepository resetPasswordRequestRepository;

    @Autowired
    public PasswordService(AccountRepository accountRepository, EmailService emailService, ResetPasswordRequestRepository resetPasswordRequestRepository) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.resetPasswordRequestRepository = resetPasswordRequestRepository;
    }



    public void addResetRequest(Account acc, String hostLink) throws SystemException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        ResetPasswordRequest req = new ResetPasswordRequest(acc, UUID.randomUUID().toString(), calendar.getTime());
        try {
            ResetPasswordEmail data = new ResetPasswordEmail(acc, hostLink + "/validateReset?token=" + req.getToken());
            emailService.sendEmail(data);
            resetPasswordRequestRepository.save(req);
        } catch (MailException e) {
            throw new SystemException();
        }

    }

    public int identifyAccount(String token) throws InvalidTokenException {
        Date now = new Date();
        Optional<ResetPasswordRequest> requestOptional = resetPasswordRequestRepository.findByToken(token);
        if(!requestOptional.isPresent() || requestOptional.get().getExpirationDate().compareTo(now) < 0)
            throw new InvalidTokenException();

        return requestOptional.get().getAccount().getId();
    }

    public void validatePassword(String password, String verification) throws SignUpException {
        if(!password.equals(verification))
            throw new PasswordVerificationException();

        int iPasswordScore = 0;

        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;

        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;

        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;

        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;

        if( password.length() < 8 || iPasswordScore < 4)
            throw new WeakPasswordException();
    }

    public void updateAccountPasswordById(int id, String password) throws SystemException {
        try {
            Optional<Account> accountOptional = accountRepository.findById(id);
            if(!accountOptional.isPresent()) {
                throw new NullPointerException();
            }
            Account account = accountOptional.get();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPass = password+account.getSalt();
            account.setPassword(DatatypeConverter.printHexBinary(md.digest(saltedPass.getBytes(StandardCharsets.UTF_8))));
            accountRepository.save(account);
            resetPasswordRequestRepository.deleteAllByAccount(account);
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            throw new SystemException();
        }
    }

}