package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.domain.Student.StudentStatus;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.*;
import edu.ccrm.util.Comparators;
import edu.ccrm.util.RecursionUtils;
import edu.ccrm.util.Validators;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
    private final TranscriptService transcriptService = new TranscriptService(studentService, courseService);
    private final ImportExportService importExportService = new ImportExportService(studentService, courseService);
    private final BackupService backupService = new BackupService();

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        // Load initial data
        try {
            menu.importExportService.importAllData();
        } catch (IOException e) {
            System.err.println("Failed to load initial data: " + e.getMessage());
        }
        menu.run();
    }

    public void run() {
        int choice;
        do {
            printMainMenu();
            choice = getIntInput("Choose an option: ");
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageCourses();
                case 3 -> manageEnrollments();
                case 4 -> manageData();
                case 5 -> manageSystem();
                case 0 -> System.out.println("Exiting CCRM. Goodbye!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("\n===================================================");
        System.out.println("   Campus Course & Records Manager (CCRM)");
        System.out.println("===================================================");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments & Grades");
        System.out.println("4. Import / Export Data");
        System.out.println("5. System Utilities & Reports");
        System.out.println("0. Exit");
        System.out.println("===================================================");
        System.out.print("Choose an option: ");
    }

    private void manageStudents() {
        int choice;
        do {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add a new Student");
            System.out.println("2. List all Students");
            System.out.println("3. Update a Student");
            System.out.println("4. Deactivate a Student");
            System.out.println("5. Print Student Transcript");
            System.out.println("0. Back to Main Menu");
            choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listStudents();
                case 3 -> updateStudent();
                case 4 -> deactivateStudent();
                case 5 -> printTranscript();
                case 0 -> {}
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void addStudent() {
        String name = getStringInput("Enter Full Name: ");
        String email;
        while (true) {
            email = getStringInput("Enter Email: ");
            if (Validators.isValidEmail(email)) {
                break;
            }
            System.out.println("Invalid email format. Please try again.");
        }
        Student student = new Student(name, email);
        studentService.addStudent(student);
        System.out.println("Student added successfully with RegNo: " + student.getRegNo());
    }

    private void listStudents() {
        List<Student> students = studentService.getAllStudents();
        students.sort(Comparators.STUDENT_NAME_COMPARATOR);
        System.out.println("\n--- All Students ---");
        students.forEach(System.out::println);
    }

    private void updateStudent() {
        Student student = getStudentFromInput();
        if (student == null) return;

        System.out.print("Enter new Full Name (or press Enter to keep '" + student.getFullName() + "'): ");
        String name = scanner.nextLine();
        if (Validators.isNotNullOrEmpty(name)) {
            student.setFullName(name);
        }

        System.out.print("Enter new Email (or press Enter to keep '" + student.getEmail() + "'): ");
        String email = scanner.nextLine();
        if (Validators.isNotNullOrEmpty(email)) {
            if (Validators.isValidEmail(email)) {
                student.setEmail(email);
            } else {
                System.out.println("Invalid email format. Email not updated.");
            }
        }
        System.out.println("Student updated successfully.");
    }

    private void deactivateStudent() {
        Student student = getStudentFromInput();
        if (student != null) {
            student.setStatus(StudentStatus.INACTIVE);
            System.out.println("Student " + student.getRegNo() + " deactivated.");
        }
    }

    private void printTranscript() {
        Student student = getStudentFromInput();
        if (student == null) return;
        try {
            String transcript = transcriptService.generateTranscript(student.getRegNo());
            System.out.println(transcript);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void manageCourses() {
        // Implementation for course management menu
        int choice;
        do {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add a new Course");
            System.out.println("2. List all Courses");
            System.out.println("3. Search/Filter Courses");
            System.out.println("0. Back to Main Menu");
            choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> addCourse();
                case 2 -> listCourses();
                case 3 -> searchCourses();
                case 0 -> {}
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void addCourse() {
        String code = getStringInput("Enter Course Code (e.g., CS101): ");
        String title = getStringInput("Enter Course Title: ");
        int credits = getIntInput("Enter Credits: ");
        String instructor = getStringInput("Enter Instructor Name: ");
        Semester semester = getSemesterInput("Enter Semester (SPRING, SUMMER, FALL): ");
        String department = getStringInput("Enter Department: ");

        Course course = new Course.Builder(code, title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(department)
                .build();
        courseService.addCourse(course);
        System.out.println("Course added successfully.");
    }

    private void listCourses() {
        List<Course> courses = courseService.getAllCourses();
        courses.sort(Comparators.COURSE_TITLE_COMPARATOR);
        System.out.println("\n--- All Courses ---");
        System.out.printf("%-10s | %-30s | %-2s | %-20s | %-10s | %-20s\n", "Code", "Title", "Cr", "Instructor", "Semester", "Department");
        System.out.println("-".repeat(105));
        courses.forEach(System.out::println);
    }
    
    private void searchCourses() {
        System.out.println("Filter by: 1. Instructor, 2. Department, 3. Semester");
        int choice = getIntInput("Choose an option: ");
        List<Course> results;
        switch(choice) {
            case 1:
                String instructor = getStringInput("Enter instructor name: ");
                results = courseService.filterByInstructor(instructor);
                break;
            case 2:
                String dept = getStringInput("Enter department name: ");
                results = courseService.filterByDepartment(dept);
                break;
            case 3:
                Semester semester = getSemesterInput("Enter semester (SPRING, SUMMER, FALL): ");
                results = courseService.filterBySemester(semester);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        System.out.println("\n--- Filtered Courses ---");
        results.forEach(System.out::println);
    }

    private void manageEnrollments() {
        int choice;
        do {
            System.out.println("\n--- Enrollments & Grades ---");
            System.out.println("1. Enroll a Student in a Course");
            System.out.println("2. Assign Grade to Student");
            System.out.println("0. Back to Main Menu");
            choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> enrollStudent();
                case 2 -> assignGrade();
                case 0 -> {}
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void enrollStudent() {
        Student student = getStudentFromInput();
        if (student == null) return;
        Course course = getCourseFromInput();
        if (course == null) return;

        try {
            enrollmentService.enrollStudent(student.getRegNo(), course.getCourseCode().getCode());
            System.out.println("Enrollment successful.");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException | IllegalArgumentException e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        }
    }

    private void assignGrade() {
        Student student = getStudentFromInput();
        if (student == null) return;
        Course course = getCourseFromInput();
        if (course == null) return;
        
        Grade grade = getGradeInput("Enter Grade (S, A, B, C, D, E, F): ");

        try {
            enrollmentService.assignGrade(student.getRegNo(), course.getCourseCode().getCode(), grade);
            System.out.println("Grade assigned successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println("Grade assignment failed: " + e.getMessage());
        }
    }

    private void manageData() {
        System.out.println("\n--- Import / Export Data ---");
        System.out.println("1. Import all data from CSV");
        System.out.println("2. Export all data to CSV");
        int choice = getIntInput("Choose an option: ");
        try {
            if (choice == 1) {
                importExportService.importAllData();
                System.out.println("Data imported successfully.");
            } else if (choice == 2) {
                importExportService.exportAllData();
                System.out.println("Data exported successfully.");
            }
        } catch (IOException e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
    }

    private void manageSystem() {
        System.out.println("\n--- System Utilities & Reports ---");
        System.out.println("1. Create a new backup");
        System.out.println("2. Show total backup size");
        System.out.println("3. Show GPA Distribution Report");
        System.out.println("4. Show Top N Students Report");
        System.out.println("5. Show Course Enrollment Statistics");
        int choice = getIntInput("Choose an option: ");
        try {
            switch(choice) {
                case 1:
                    backupService.performBackup();
                    break;
                case 2:
                    Path backupPath = AppConfig.getInstance().getBackupPath();
                    long size = RecursionUtils.calculateDirectorySize(backupPath);
                    System.out.printf("Total backup size: %d bytes (%.2f MB)\n", size, size / (1024.0 * 1024.0));
                    break;
                case 3:
                    System.out.println("\n--- GPA Distribution ---");
                    transcriptService.getGpaDistribution().forEach((name, gpa) -> 
                        System.out.printf("  %-20s | %.2f\n", name, gpa));
                    break;
                case 4:
                    int n = getIntInput("Enter the number of top students to show: ");
                    System.out.println("\n--- Top " + n + " Students by GPA ---");
                    transcriptService.getTopNStudents(n).forEach((name, gpa) ->
                        System.out.printf("  %-20s | %.2f\n", name, gpa));
                    break;
                case 5:
                    System.out.println("\n--- Course Enrollment Statistics ---");
                    transcriptService.getCourseEnrollmentStats().forEach((title, count) ->
                        System.out.printf("  %-30s | %d student(s)\n", title, count));
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (IOException e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
    }

    // --- Robust Input Helper Methods ---

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private String getStringInput(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (Validators.isNotNullOrEmpty(input)) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private Semester getSemesterInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt).toUpperCase();
            try {
                return Semester.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid semester. Please enter SPRING, SUMMER, or FALL.");
            }
        }
    }

    private Grade getGradeInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt).toUpperCase();
            try {
                return Grade.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid grade. Please enter S, A, B, C, D, E, or F.");
            }
        }
    }

    private Student getStudentFromInput() {
        while (true) {
            String regNo = getStringInput("Enter Student RegNo: ");
            var studentOpt = studentService.findStudentByRegNo(regNo);
            if (studentOpt.isPresent()) {
                return studentOpt.get();
            }
            System.out.println("Student with registration number '" + regNo + "' not found. Please try again.");
            System.out.print("Press Enter to try again or type 'exit' to cancel: ");
            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                return null;
            }
        }
    }

    private Course getCourseFromInput() {
        while (true) {
            String courseCode = getStringInput("Enter Course Code: ");
            var courseOpt = courseService.findCourseByCode(courseCode);
            if (courseOpt.isPresent()) {
                return courseOpt.get();
            }
            System.out.println("Course with code '" + courseCode + "' not found. Please try again.");
            System.out.print("Press Enter to try again or type 'exit' to cancel: ");
            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                return null;
            }
        }
    }


}
