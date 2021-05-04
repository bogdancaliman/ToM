package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.models.Account;
import com.project.project.models.Feedback;
import com.project.project.models.HolidayRequest;
import com.project.project.repositories.AccountRepository;
import com.project.project.repositories.FeedbackRepository;
import com.project.project.repositories.HolidayRequestRepository;

import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final HolidayRequestRepository holidayRequestRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, HolidayRequestRepository holidayRequestRepository, AccountRepository accountRepository) {
        this.feedbackRepository = feedbackRepository;
        this.holidayRequestRepository = holidayRequestRepository;
        this.accountRepository = accountRepository;
    }

    public void addFeedback(String requestId, String description, String username) {
        Optional<HolidayRequest> holidayRequestOptional = holidayRequestRepository.findById(requestId);
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(holidayRequestOptional.isPresent() && accountOptional.isPresent())
            feedbackRepository.save(new Feedback(holidayRequestOptional.get(), description, accountOptional.get()));
    }
}