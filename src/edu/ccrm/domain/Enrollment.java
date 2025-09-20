package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * Represents the enrollment of a student in a specific course.
 *
 * DEMONSTRATES:
 * - Association class: Links Student and Course.
 * - Use of the Date/Time API (LocalDateTime).
 */
public class Enrollment {
    private final String studentRegNo;
    private final CourseCode courseCode;
    private Grade grade;
    private final LocalDateTime enrollmentDate;

    public Enrollment(String studentRegNo, CourseCode courseCode) {
        this.studentRegNo = studentRegNo;
        this.courseCode = courseCode;
        this.enrollmentDate = LocalDateTime.now();
        this.grade = null; // Grade is assigned later
    }

    // Getters and Setters
    public String getStudentRegNo() {
        return studentRegNo;
    }

    public CourseCode getCourseCode() {
        return courseCode;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    @Override
    public String toString() {
        return String.format("Course: %s, Enrolled: %s, Grade: %s",
                courseCode, enrollmentDate.toLocalDate(), grade != null ? grade : "Not Graded");
    }
}
