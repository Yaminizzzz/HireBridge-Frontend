package com.simats.hirebridge;
import java.io.Serializable;

public class Job implements Serializable {
    private String title;
    private String company;
    private String location;
    private String salary;
    private String experience;
    private String status;

    public Job(String title, String company, String location, String salary, String experience, String status) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.experience = experience;
        this.status = status;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public String getSalary() { return salary; }
    public String getExperience() { return experience; }
    public String getStatus() { return status; }
}
