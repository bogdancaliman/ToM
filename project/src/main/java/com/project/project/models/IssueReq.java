package com.project.project.models;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "IssueRequest")
@Table(name = "issue_req")
public class IssueReq implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "FK_employee")
    private Account account;

    
}