package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.MissingDepartmentException;
import com.project.project.exceptions.SignUpException;
import com.project.project.exceptions.UsedEmailException;
import com.project.project.models.Department;
import com.project.project.models.Employee;

import com.project.project.repositories.DepartmentRepository;
import com.project.project.repositories.EmployeeRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HRService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public HRService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Iterable<Department> loadDepartments() {
        return departmentRepository.findAll();
    }

    public List<Employee> loadEmployeesOfDepartmentById(int departmentId) {
        return employeeRepository.findAllByDepartment_Id(departmentId);
    }

    public void checkIfEmailIsAvailable(Map<String, String> params) throws SignUpException {
        if (employeeRepository.findByEmail(params.get("email")).isPresent())
            throw new UsedEmailException();
    }
    public int addEmployee(Map<String, String> params) throws MissingDepartmentException {
        Date date;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(params.get("date"));
        } catch (ParseException e) {
            date = new Date();
        }

        Optional<Department> departmentOptional = departmentRepository.findById(Integer.parseInt(params.get("departmentId")));
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
        throw new MissingDepartmentException();
    }
}