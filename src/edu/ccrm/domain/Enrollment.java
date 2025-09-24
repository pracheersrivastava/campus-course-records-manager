








package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final String studentRegNo;
    private final CourseCode courseCode;
    private Grade grade;
    private final LocalDateTime enrollmentDate;

    public Enrollment(String studentRegNo, CourseCode courseCode) {
        this.studentRegNo = studentRegNo;
        this.courseCode = courseCode;
        this.enrollmentDate = LocalDateTime.now();
        this.grade = null;
    }

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
