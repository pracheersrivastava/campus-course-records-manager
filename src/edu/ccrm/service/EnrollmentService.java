package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

import java.util.Optional;

public class EnrollmentService {

    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void enrollStudent(String regNo, String courseCode) throws DuplicateEnrollmentException {
        Student student = studentService.findStudentByRegNo(regNo)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + regNo));
        Course course = courseService.findCourseByCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseCode));

        // Rule: Check for duplicate enrollment
        boolean alreadyEnrolled = student.getEnrolledCourses().stream()
                .anyMatch(e -> e.getCourseCode().equals(course.getCourseCode()));
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException("Student " + regNo + " is already enrolled in course " + courseCode);
        }

        // Rule: Check for max credits per semester
        int maxCredits = AppConfig.getInstance().getIntProperty("max.credits.per.semester");
        int currentCredits = student.getEnrolledCourses().stream()
                .map(e -> courseService.findCourseByCode(e.getCourseCode().getCode()).orElse(null))
                .filter(c -> c != null && c.getSemester() == course.getSemester())
                .mapToInt(Course::getCredits)
                .sum();

        if (currentCredits + course.getCredits() > maxCredits) {
            throw new MaxCreditLimitExceededException("Enrollment failed: Exceeds max credit limit of " + maxCredits + " for the semester.");
        }

        Enrollment enrollment = new Enrollment(regNo, course.getCourseCode());
        student.addEnrollment(enrollment);
    }

    public void assignGrade(String regNo, String courseCode, Grade grade) {
        Student student = studentService.findStudentByRegNo(regNo)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + regNo));

        Optional<Enrollment> enrollmentOpt = student.getEnrolledCourses().stream()
                .filter(e -> e.getCourseCode().getCode().equalsIgnoreCase(courseCode))
                .findFirst();

        if (enrollmentOpt.isPresent()) {
            enrollmentOpt.get().setGrade(grade);
        } else {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }
    }
}
