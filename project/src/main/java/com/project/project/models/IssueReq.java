package com.project.project.models;

import javax.persistence.*;
import java.util.Objects;
import java.io.Serializable;

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

    public IssueReq() {
    }

    public IssueReq(String description, Account account) {
        this.description = description;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueReq issueReq = (IssueReq) o;
        return getId() == issueReq.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getAccount());
    }

    @Override
    public String toString() {
        return "IssueReq{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", account=" + account +
                '}';
    }
}