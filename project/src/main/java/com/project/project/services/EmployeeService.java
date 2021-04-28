package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.project.dtos.CalendarEvent;
import com.project.project.dtos.RequestType;
import com.project.project.exceptions.FileStorageException;
import com.project.project.exceptions.NotEnoughDaysException;
import com.project.project.models.Account;
import com.project.project.models.HolidayRequest;
import com.project.project.dtos.RequestStatus;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.repositories.AccountRepository;
import com.project.project.repositories.HolidayRequestRepository;
import com.project.project.models.Employee;
import com.project.project.repositories.EmployeeRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeeService {

    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(AccountRepository accountRepository, EmployeeRepository employeeRepository) {
        this.accountRepository = accountRepository;
        this.employeeRepository = employeeRepository;
        this.uploadedFileService = uploadedFileService;
    }

    public void removeEmployee(String employeeId) {
        Optional<Employee> accountOptional = employeeRepository.findById(employeeId);
        if (accountOptional.isPresent()) {
            employeeRepository.deleteById(employeeId);
        }
    }

    public void updateTeamLeader(String employeeId1, String employeeId2) {
        Optional<Employee> employeeOptional1 = employeeRepository.findById(employeeId1);
        Optional<Employee> employeeOptional2 = employeeRepository.findById(employeeId2);
        if (employeeOptional1.isPresent() && employeeOptional2.isPresent()) {
            Account account = employeeOptional1.get().getAccount();
            account.setTeamLeader(employeeOptional2.get().getAccount());
            accountRepository.save(account);
        }
    }
}