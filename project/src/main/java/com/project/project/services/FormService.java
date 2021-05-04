package com.project.project.services;

import org.springframework.stereotype.Service;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.models.Department;
import com.project.project.repositories.AccountRepository;
import com.project.project.repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    private final DepartmentRepository departmentRepository;
    private final AccountRepository accountRepository;

    public FormService(DepartmentRepository departmentRepository, AccountRepository accountRepository) {
        this.departmentRepository = departmentRepository;
        this.accountRepository = accountRepository;
    }

    public List<Account> loadAccountsOfDepartmentById(String departmentId) {
        return accountRepository.findAllByEmployee_Department_Id(departmentId);
    }

    public List<Account> loadAccountsOfDepartmentByIdExceptMe(String departmentId, String username) {
        return accountRepository.findAllByEmployee_Department_IdAndUsernameNot(departmentId, username);
    }

    public Iterable<Department> loadDepartments() {
        return departmentRepository.findAll();
    }

    public List<Account> loadPossibleDelegates(String username) throws UserNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            List<Account> myTeam = new ArrayList<>(account.getMembers());
            if (myTeam.isEmpty()) return null;
            else {
                List<Account> colleagues;
                if (account.getTeamLeader() == null) {
                    colleagues = accountRepository.findAllByTeamLeaderIsNullAndEmployee_Department(account.getEmployee().getDepartment());
                    colleagues.remove(account);
                    if (colleagues.size() != 0)
                        return colleagues;
                    else return myTeam;
                } else {
                    colleagues = new ArrayList<>(account.getTeamLeader().getMembers());
                    colleagues.remove(account);
                    colleagues.add(account.getTeamLeader());
                    return colleagues;
                }
            }
        } else
            throw new UserNotFoundException("User " + username + " was not found!");
    }
}