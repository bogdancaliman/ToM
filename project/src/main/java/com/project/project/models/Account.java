package com.project.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.project.models.ResetPasswordRequest;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Account")
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String username;
    private String password;
    private String salt;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IssueRequest> issueRequests = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HolidayRequest> sentHolidayRequests = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "delegate", cascade = CascadeType.ALL)
    private Set<HolidayRequest> delegatedHolidayRequests = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResetPasswordRequest> resetPasswordRequests = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "teamLeader", cascade = CascadeType.ALL)
    private Set<Account> members = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "FK_team_leader")
    private Account teamLeader;

    @OneToOne
    @JoinColumn(name = "FK_employee")
    private Employee employee;

    public Account(String username, String password, String salt, Employee employee, Account teamLeader) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.employee = employee;
        this.teamLeader = teamLeader;
    }
}