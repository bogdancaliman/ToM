package com.project.project.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "ResetPassReq")
@Table(name = "reset_pass_req")
public class ResetPassReq {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "FK_account_id")
    private Account account_req;
    private String token;
    private Date expirationDate;
}