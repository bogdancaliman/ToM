package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.repositories.ResetPassReqRepository;

import java.util.Date;

@Service
public class AppRefreshService {
    @Autowired
    private ResetPassReqRepository resetPassReqRepository;

    public void refreshData() {
        resetPassReqRepository.deleteAllByExpirationDateIsLessThanEqual(new Date());
    }
}