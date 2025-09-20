package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TranscriptService {

    private final StudentService studentService;
    private final CourseService courseService;

    public TranscriptService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public String generateTranscript(String regNo) {
        Student student = studentService.findStudentByRegNo(regNo)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + regNo));

        StringBuilder transcript = new StringBuilder();
        transcript.append("========================================\n");
        transcript.append("           ACADEMIC TRANSCRIPT          \n");
        transcript.append("========================================\n");
        transcript.append(student.getDetails()).append("\n\n");

        List<Enrollment> enrollments = student.getEnrolledCourses();
        if (enrollments.isEmpty()) {
            transcript.append("No courses enrolled.\n");
        } else {
            transcript.append(String.format("%-10s | %-30s | %-7s | %-5s\n", "Code", "Course Title", "Credits", "Grade"));
            transcript.append("------------------------------------------------------------\n");

            for (Enrollment enrollment : enrollments) {
                Course course = courseService.findCourseByCode(enrollment.getCourseCode().getCode()).orElse(null);
                if (course != null) {
                    transcript.append(String.format("%-10s | %-30s | %-7d | %-5s\n",
                            course.getCourseCode(),
                            course.getTitle(),
                            course.getCredits(),
                            enrollment.getGrade() != null ? enrollment.getGrade() : "N/A"));
                }
            }
        }

        transcript.append("\nGPA: ").append(String.format("%.2f", calculateGpa(student))).append("\n");
        transcript.append("========================================\n");

        return transcript.toString();
    }

    public double calculateGpa(Student student) {
        List<Enrollment> gradedEnrollments = student.getEnrolledCourses().stream()
                .filter(e -> e.getGrade() != null)
                .collect(Collectors.toList());

        if (gradedEnrollments.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment enrollment : gradedEnrollments) {
            Course course = courseService.findCourseByCode(enrollment.getCourseCode().getCode()).orElse(null);
            if (course != null) {
                totalPoints += enrollment.getGrade().getGradePoint() * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
    
    public Map<String, Double> getGpaDistribution() {
        return studentService.getAllStudents().stream()
            .collect(Collectors.toMap(
                Student::getFullName,
                this::calculateGpa
            ));
    }

    /**
     * Finds the top N students based on their GPA.
     * @param n The number of top students to return.
     * @return A map of student names to their GPAs, sorted in descending order of GPA.
     */
    public Map<String, Double> getTopNStudents(int n) {
        return studentService.getAllStudents().stream()
            .sorted((s1, s2) -> Double.compare(calculateGpa(s2), calculateGpa(s1)))
            .limit(n)
            .collect(Collectors.toMap(
                Student::getFullName,
                this::calculateGpa,
                (e1, e2) -> e1,
                java.util.LinkedHashMap::new // Preserve insertion order
            ));
    }

    /**
     * Calculates and returns the enrollment count for each course.
     * @return A map where the key is the course title and the value is the number of students enrolled.
     */
    public Map<String, Long> getCourseEnrollmentStats() {
        return studentService.getAllStudents().stream()
            .flatMap(student -> student.getEnrolledCourses().stream())
            .map(Enrollment::getCourseCode)
            .collect(Collectors.groupingBy(
                courseCode -> courseService.findCourseByCode(courseCode.getCode())
                                           .map(Course::getTitle)
                                           .orElse("Unknown Course"),
                Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                java.util.LinkedHashMap::new
            ));
    }
}
