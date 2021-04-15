package com.project.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class WebEvent implements Serializable {
    private int id;
    private String title;
    private Date start;
    private Date end;
    private String color;
}