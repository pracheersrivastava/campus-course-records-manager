package edu.ccrm.domain;

/**
 * Represents a course offered by the campus.
 *
 * DEMONSTRATES:
 * - Builder Design Pattern: For constructing complex objects step-by-step.
 * - Encapsulation.
 * - Use of enums (Semester).
 * - Inner Class: The Builder class is an inner class.
 */
public class Course {

    private final CourseCode courseCode;
    private final String title;
    private final int credits;
    private final String instructor;
    private final Semester semester;
    private final String department;
    private boolean active;

    // Private constructor to be used by the Builder
    private Course(Builder builder) {
        this.courseCode = builder.courseCode;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        this.active = true; // Active by default
    }

    /**
     * DEMONSTRATES: Inner Class (non-static).
     * The Builder class for constructing a Course instance.
     */
    public static class Builder {
        private CourseCode courseCode;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;

        public Builder(String code, String title) {
            this.courseCode = new CourseCode(code);
            this.title = title;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Course build() {
            // Validation can be done here
            return new Course(this);
        }
    }

    // Getters
    public CourseCode getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public Semester getSemester() {
        return semester;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-30s | %-2d | %-20s | %-10s | %-20s",
                courseCode, title, credits, instructor, semester, department);
    }
}
