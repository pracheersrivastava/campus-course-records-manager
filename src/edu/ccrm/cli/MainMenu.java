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
            choice = getIntInput();
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
            System.out.print("Choose an option: ");
            choice = getIntInput();

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
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (!Validators.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
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
        System.out.print("Enter Student RegNo to update: ");
        String regNo = scanner.nextLine();
        studentService.findStudentByRegNo(regNo).ifPresentOrElse(student -> {
            System.out.print("Enter new Full Name (or press Enter to keep current): ");
            String name = scanner.nextLine();
            if (Validators.isNotNullOrEmpty(name)) {
                student.setFullName(name);
            }
            System.out.print("Enter new Email (or press Enter to keep current): ");
            String email = scanner.nextLine();
            if (Validators.isNotNullOrEmpty(email)) {
                student.setEmail(email);
            }
            System.out.println("Student updated successfully.");
        }, () -> System.out.println("Student not found."));
    }

    private void deactivateStudent() {
        System.out.print("Enter Student RegNo to deactivate: ");
        String regNo = scanner.nextLine();
        studentService.findStudentByRegNo(regNo).ifPresentOrElse(
            s -> {
                s.setStatus(StudentStatus.INACTIVE);
                System.out.println("Student deactivated.");
            },
            () -> System.out.println("Student not found.")
        );
    }

    private void printTranscript() {
        System.out.print("Enter Student RegNo: ");
        String regNo = scanner.nextLine();
        try {
            String transcript = transcriptService.generateTranscript(regNo);
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
            System.out.print("Choose an option: ");
            choice = getIntInput();

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
        System.out.print("Enter Course Code (e.g., CS101): ");
        String code = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = getIntInput();
        System.out.print("Enter Instructor Name: ");
        String instructor = scanner.nextLine();
        System.out.print("Enter Semester (SPRING, SUMMER, FALL): ");
        Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

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
        int choice = getIntInput();
        List<Course> results;
        switch(choice) {
            case 1:
                System.out.print("Enter instructor name: ");
                String instructor = scanner.nextLine();
                results = courseService.filterByInstructor(instructor);
                break;
            case 2:
                System.out.print("Enter department name: ");
                String dept = scanner.nextLine();
                results = courseService.filterByDepartment(dept);
                break;
            case 3:
                System.out.print("Enter semester (SPRING, SUMMER, FALL): ");
                Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
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
            System.out.print("Choose an option: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> enrollStudent();
                case 2 -> assignGrade();
                case 0 -> {}
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void enrollStudent() {
        System.out.print("Enter Student RegNo: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        try {
            enrollmentService.enrollStudent(regNo, courseCode);
            System.out.println("Enrollment successful.");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException | IllegalArgumentException e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        }
    }

    private void assignGrade() {
        System.out.print("Enter Student RegNo: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
        try {
            Grade grade = Grade.valueOf(scanner.nextLine().toUpperCase());
            enrollmentService.assignGrade(regNo, courseCode, grade);
            System.out.println("Grade assigned successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid grade or input. " + e.getMessage());
        }
    }

    private void manageData() {
        System.out.println("\n--- Import / Export Data ---");
        System.out.println("1. Import all data from CSV");
        System.out.println("2. Export all data to CSV");
        System.out.print("Choose an option: ");
        int choice = getIntInput();
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
        System.out.print("Choose an option: ");
        int choice = getIntInput();
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
                default:
                    System.out.println("Invalid option.");
            }
        } catch (IOException e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1; // Indicates an error
        }
    }
}
