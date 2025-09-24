








package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private String department;
    private final List<CourseCode> coursesTaught;

    public Instructor(String fullName, String email, String department) {
        super(fullName, email);
        this.department = department;
        this.coursesTaught = new ArrayList<>();
    }

    @Override
    public String getDetails() {
        return String.format("Instructor Profile:%n  ID: %d%n  Name: %s%n  Department: %s",
                id, fullName, department);
    }

    public void addCourse(CourseCode courseCode) {
        if (!coursesTaught.contains(courseCode)) {
            coursesTaught.add(courseCode);
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<CourseCode> getCoursesTaught() {
        return new ArrayList<>(coursesTaught);
    }
}
