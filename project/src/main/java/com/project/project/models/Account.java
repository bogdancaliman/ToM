package com.project.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.project.models.ResetPassReq;
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

    @OneToOne
    @JoinColumn(name = "FK_employee")
    private Employee employee;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<IssueReq> issueReqs= new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "accountReq", cascade = CascadeType.ALL)
    private Set<HolidayReq> sentHolidayReqs= new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "accountTl", cascade = CascadeType.ALL)
    private Set<HolidayReq> receivedHolidayReqs= new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "tl", cascade = CascadeType.ALL)
    private Set<Account> members = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<ResetPassReq> resetPassReqs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "FK_tl")
    private Account tl;

     public Account(String username, String password, String salt, Employee employee, Account tl) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.employee = employee;
        this.tl = tl;
    }
}