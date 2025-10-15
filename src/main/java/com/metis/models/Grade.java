package com.metis.models;

public class Grade {
    private String course;
    private double credits;
    private double points;

    public Grade(String course, double credits, double points) {
        this.course = course;
        this.credits = credits;
        this.points = points;
    }

    public String getCourse() { return course; }
    public double getCredits() { return credits; }
    public double getPoints() { return points; }
}

