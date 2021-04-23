package com.project.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PendingIssue {


    private int id;
    private String departmentName;
    private String name;
    private String description;



}