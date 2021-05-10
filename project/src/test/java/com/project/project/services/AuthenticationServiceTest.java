package com.project.project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.models.Employee;
import com.project.project.repositories.AccountRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void getSaltOfUserShouldReturnEmptyStringIfUserNotFound() {
        assertThat(authenticationService.getSaltOfUser(anyString()).equals("")).isTrue();
    }

    @Test
    void getSaltOfUserShouldReturnASpecificStringForAFoundUser() {
        Account account = new Account();
        account.setSalt("salt");
        when(accountRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(account));
        assertThat(authenticationService.getSaltOfUser(anyString()).equals("salt")).isTrue();
    }

    @Test
    void getMyEmployeeDataShouldThrowUserNotFoundExceptionIfUserNotFound() {
        Throwable throwable = catchThrowable(()->authenticationService.getMyEmployeeData(anyString()));
        assertThat(throwable).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getMyEmployeeDataShouldReturnEmployeeDataIfUserFound() {
        Account account = new Account();
        Employee employee = new Employee();
        account.setEmployee(employee);
        when(accountRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(account));
        try {
            assertThat(account.getEmployee()==authenticationService.getMyEmployeeData(anyString())).isTrue();
        } catch (Exception e) {
            fail("Exception interfered!");
        }
    }

    @Test
    void amITeamLeaderShouldBeFalseIfUserNotFound() {
        assertThat(authenticationService.amITeamLeader(anyString())).isFalse();
    }

    @Test
    void amITeamLeaderShouldBeFalseIfUserFoundButNoMembersInTeam() {
        Account account = new Account();
        when(accountRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(account));
        assertThat(authenticationService.amITeamLeader(anyString())).isFalse();
    }

    @Test
    void amITeamLeaderShouldBeTrueIfUserFoundButMembersInTeam() {
        Account account = new Account();
        Set<Account> set = new LinkedHashSet<>();
        set.add(new Account());
        account.setMembers(set);
        when(accountRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(account));
        assertThat(authenticationService.amITeamLeader(anyString())).isTrue();
    }
}