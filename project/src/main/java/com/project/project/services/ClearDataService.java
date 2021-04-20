package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.repositories.ResetPasswordRequestRepository;

import java.util.Date;

@Service
public class ClearDataService {

    private final ResetPasswordRequestRepository resetPasswordRequestRepository;

    @Autowired
    public ClearDataService(ResetPasswordRequestRepository resetPasswordRequestRepository) {
        this.resetPasswordRequestRepository = resetPasswordRequestRepository;
    }

    public void clearData() {
        resetPasswordRequestRepository.deleteAllByExpirationDateIsLessThanEqual(new Date());
    }
}
