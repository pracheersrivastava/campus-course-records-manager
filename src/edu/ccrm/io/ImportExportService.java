package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for importing and exporting data from/to CSV files.
 *
 * DEMONSTRATES:
 * - NIO.2 API (Files, Path).
 * - Streams API for file processing.
 * - Exception handling for I/O operations.
 */
public class ImportExportService {

    private final StudentService studentService;
    private final CourseService courseService;
    private final Path dataDir;
    private final Path studentDataFile;
    private final Path courseDataFile;

    public ImportExportService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.dataDir = AppConfig.getInstance().getDataPath();
        this.studentDataFile = dataDir.resolve(AppConfig.getInstance().getProperty("students.csv.name"));
        this.courseDataFile = dataDir.resolve(AppConfig.getInstance().getProperty("courses.csv.name"));
        
        try {
            if (Files.notExists(dataDir)) {
                Files.createDirectories(dataDir);
            }
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }

    public void importAllData() throws IOException {
        importStudents();
        importCourses();
    }

    public void exportAllData() throws IOException {
        exportStudents();
        exportCourses();
    }

    private void importStudents() throws IOException {
        Path sourcePath = Paths.get("test-data", "students.csv");
        if (Files.exists(sourcePath)) {
            try (Stream<String> lines = Files.lines(sourcePath).skip(1)) { // Skip header
                List<Student> students = lines
                        .map(CsvParser::parseStudent)
                        .collect(Collectors.toList());
                studentService.loadStudents(students);
                System.out.println(students.size() + " students imported.");
            }
        }
    }

    private void importCourses() throws IOException {
        Path sourcePath = Paths.get("test-data", "courses.csv");
        if (Files.exists(sourcePath)) {
            try (Stream<String> lines = Files.lines(sourcePath).skip(1)) { // Skip header
                List<Course> courses = lines
                        .map(CsvParser::parseCourse)
                        .collect(Collectors.toList());
                courseService.loadCourses(courses);
                System.out.println(courses.size() + " courses imported.");
            }
        }
    }

    private void exportStudents() throws IOException {
        List<String> lines = studentService.getAllStudents().stream()
                .map(CsvParser::studentToCsv)
                .collect(Collectors.toList());
        lines.add(0, "regNo,fullName,email,status"); // Header
        Files.write(studentDataFile, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Student data exported to " + studentDataFile);
    }

    private void exportCourses() throws IOException {
        List<String> lines = courseService.getAllCourses().stream()
                .map(CsvParser::courseToCsv)
                .collect(Collectors.toList());
        lines.add(0, "code,title,credits,instructor,semester,department"); // Header
        Files.write(courseDataFile, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Course data exported to " + courseDataFile);
    }
}
