package com.project.project.models;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Employee")
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;
    private String address;
    private String tel;
    private int salary;
    private String email;
    private Date emp_date;

    @ManyToOne
    @JoinColumn(name = "FK_department")
    private Department department;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    public Employee(String name, String address, String tel, int salary, String email, Date emp_date, Department department) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.salary = salary;
        this.email = email;
        this.emp_date = emp_date;
        this.department = department;
    }
}