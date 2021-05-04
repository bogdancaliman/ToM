package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.MissingDepartmentException;
import com.project.project.exceptions.SignUpException;
import com.project.project.exceptions.UsedEmailException;
import com.project.project.models.*;
import com.project.project.repositories.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Service
public class HRService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final HolidayRequestRepository holidayRequestRepository;
    private final FeedbackRepository feedbackRepository;
    private final AccountRepository accountRepository;
    
    @Autowired
    public HRService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, HolidayRequestRepository holidayRequestRepository, FeedbackRepository feedbackRepository, AccountRepository accountRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.holidayRequestRepository = holidayRequestRepository;
        this.feedbackRepository = feedbackRepository;
        this.accountRepository = accountRepository;
    }

    public void checkIfEmailIsAvailable(Map<String, String> params) throws SignUpException {
        if (employeeRepository.findByEmail(params.get("email")).isPresent())
            throw new UsedEmailException("The email is already used!");
    }

    public String addEmployee(Map<String, String> params) throws MissingDepartmentException {
        Date date;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(params.get("emp-date"));
        } catch (ParseException e) {
            date = new Date();
        }

        Optional<Department> departmentOptional = departmentRepository.findById(params.get("departmentId"));
        if (departmentOptional.isPresent()) {
            Employee newEmployee = new Employee(
                    params.get("name"),
                    params.get("address"),
                    params.get("phone"),
                    Integer.parseInt(params.get("salary")),
                    params.get("email"),
                    date,
                    departmentOptional.get()
            );
            employeeRepository.save(newEmployee);
            return newEmployee.getId();
        }
        throw new MissingDepartmentException("Department could not be found!");
    }
    
    public List<HolidayRequest> loadRequestsOfDepartment(String departmentId) {
        return holidayRequestRepository.findAllByRequester_Employee_Department_Id(departmentId);
    }
    
    public Feedback addFeedback(String requestId, String description, String username) {
        Optional<HolidayRequest> holidayRequestOptional = holidayRequestRepository.findById(requestId);
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(holidayRequestOptional.isPresent() && accountOptional.isPresent())
            return feedbackRepository.save(new Feedback(holidayRequestOptional.get(), description, accountOptional.get()));
        else
            return null;
    }
}