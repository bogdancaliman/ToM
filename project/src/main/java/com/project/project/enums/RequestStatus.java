package com.project.project.enums;

public enum RequestStatus {
    sentTL("Pending"),
    accTl("Accepted"),
    decTL("Declined");

    private final String displayValue;

    RequestStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}