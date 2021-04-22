package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.models.Department;
import com.project.project.models.Employee;
import com.project.project.models.IssueRequest;
import com.project.project.repositories.DepartmentRepository;
import com.project.project.repositories.EmployeeRepository;
import com.project.project.repositories.IssueRequestRepository;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final IssueRequestRepository issueRequestRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, IssueRequestRepository issueRequestRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.issueRequestRepository = issueRequestRepository;
    }

    public Iterable<Department> loadDepartments() {
        return departmentRepository.findAll();
    }

    public List<Employee> loadEmployeesOfDepartmentById(int departmentId) {
        return employeeRepository.findAllByDepartment_Id(departmentId);
    }

    public void addDepartment(String name) {
        departmentRepository.save(new Department(name));
    }

    public void removeDepartment(int departmentId) {
        StringBuilder str = new StringBuilder("The following employees don't have a department: \n");
        List<Employee> lst = employeeRepository.findAllByDepartment_Id(departmentId);
        for (Employee e : lst) {
            str.append(e.getName());
            str.append("\n");
            e.setDepartment(null);
            employeeRepository.save(e);
        }
        issueRequestRepository.save(new IssueRequest(str.toString(), null));
        departmentRepository.deleteById(departmentId);
    }
}