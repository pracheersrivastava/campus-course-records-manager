package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a student in the CCRM system.
 *
 * DEMONSTRATES:
 * - Inheritance: Extends the abstract Person class.
 * - Encapsulation: Private fields with public getters/setters.
 * - Static nested class: For generating unique registration numbers.
 * - Overriding an abstract method: getDetails().
 */
public class Student extends Person {

    public enum StudentStatus {
        ACTIVE,
        INACTIVE,
        GRADUATED
    }

    private final String regNo;
    private StudentStatus status;
    private final List<Enrollment> enrolledCourses;

    /**
     * Static nested class for generating registration numbers.
     * This is a good use case for a static nested class as it's closely related
     * to the Student class but doesn't need access to instance members of Student.
     */
    private static class RegistrationNumberGenerator {
        private static final AtomicInteger counter = new AtomicInteger(1);
        public static String generate() {
            return "STU" + String.format("%03d", counter.getAndIncrement());
        }
    }

    public Student(String fullName, String email) {
        super(fullName, email);
        this.regNo = RegistrationNumberGenerator.generate();
        this.status = StudentStatus.ACTIVE;
        this.enrolledCourses = new ArrayList<>();
    }
    
    // Constructor for CSV import
    public Student(String regNo, String fullName, String email, StudentStatus status) {
        super(fullName, email);
        this.regNo = regNo;
        this.status = status;
        this.enrolledCourses = new ArrayList<>();
    }


    @Override
    public String getDetails() {
        return String.format("Student Profile:%n  RegNo: %s%n  Name: %s%n  Email: %s%n  Status: %s",
                regNo, fullName, email, status);
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrolledCourses.add(enrollment);
    }

    // Getters and Setters
    public String getRegNo() {
        return regNo;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
        this.dateModified = java.time.LocalDateTime.now();
    }

    public List<Enrollment> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses); // Return a copy
    }
}
