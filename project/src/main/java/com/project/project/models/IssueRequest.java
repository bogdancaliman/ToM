package com.project.project.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "IssueRequest")
@Table(name = "issue_request")
public class IssueRequest implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "FK_account")
    private Account account;

    public IssueRequest(String description, Account account) {
        this.description = description;
        this.account = account;
    }
}