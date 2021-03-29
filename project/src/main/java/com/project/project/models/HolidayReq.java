package com.project.project.models;

import lombok.*;
import com.project.project.dtos.RequestStatus;
import com.project.project.dtos.RequestType;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "HolidayRequest")
@Table(name = "holiday_req")
public class HolidayReq implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.ORDINAL)
    private RequestType type;

    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;

    private String description;

    private Date start;
    private Date end;

    @ManyToOne
    @JoinColumn(name = "FK_ID_req")
    private Account account_req;

    @ManyToOne
    @JoinColumn(name = "FK_ID_tl")
    private Account account_tl;

    public HolidayReq(RequestType type, RequestStatus status, String description, Date start, Date end, Account account_req, Account account_tl) {
        this.type = type;
        this.status = status;
        this.description = description;
        this.start = start;
        this.end = end;
        this.account_req = account_req;
        this.account_tl = account_tl;
    }
}