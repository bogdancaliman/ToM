package com.project.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CalendarEvent implements Serializable {
    private String id;
    private String title;
    private Date start;
    private Date end;
    private String color;
}