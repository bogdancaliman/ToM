package com.project.project.dtos;

public enum RequestType {
    Med("Medical"),
    Rel("Rest"),
    Fam("Family situation"),
    Hof("Home Office");


    private final String displayValue;

    private RequestType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}