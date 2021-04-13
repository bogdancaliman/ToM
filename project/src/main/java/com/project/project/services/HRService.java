package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.exceptions.EmailUsedException;
import com.project.project.exceptions.SignUpException;
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


@Service
public class HRService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Iterable<Department> loadDepartments() {
        return departmentRepository.findAll();
    }

    public List<Employee> loadEmployeesOfDepartmentId(int departmentId) {
        return employeeRepository.findAllByDepartment_Id(departmentId);
    }

    public void validateFormData(Map<String, String> params) throws SignUpException {
        if(employeeRepository.findByEmail(params.get("email"))!=null)
            throw new EmailUsedException();
    }
    public int addEmployeeRecord(Map<String, String> params) {
        Date empl_date;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            empl_date = format.parse(params.get("empl_date"));
        } catch (ParseException e) {
            empl_date = new Date();
        }
        Employee newEmployee = new Employee(
                params.get("name"),
                params.get("address"),
                params.get("phone"),
                Integer.parseInt(params.get("salary")),
                params.get("email"),
                empl_date,
                departmentRepository.findById(Integer.parseInt(params.get("departmentId")))
        );
        employeeRepository.save(newEmployee);
        return newEmployee.getId();
    }
}