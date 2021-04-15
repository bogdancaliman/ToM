package com.project.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.project.project.models.IssueReq;

@Getter
@Setter
@AllArgsConstructor
public class PendingIssue {


    private int Id;
    private String depName;
    private String emplName;
    private String description;



}