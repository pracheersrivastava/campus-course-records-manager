package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Student.StudentStatus;



/**
 * A utility class for parsing CSV data into domain objects.
 *
 * DEMONSTRATES:
 * - String manipulation (split).
 * - Streams and lambda for data transformation.
 */
public class CsvParser {

    public static Student parseStudent(String csvLine) {
        String[] fields = csvLine.split(",");
        if (fields.length < 4) return null;
        return new Student(fields[0], fields[1], fields[2], StudentStatus.valueOf(fields[3].toUpperCase()));
    }

    public static Course parseCourse(String csvLine) {
        String[] fields = csvLine.split(",");
        if (fields.length < 6) return null;
        return new Course.Builder(fields[0], fields[1])
                .credits(Integer.parseInt(fields[2]))
                .instructor(fields[3])
                .semester(Semester.valueOf(fields[4].toUpperCase()))
                .department(fields[5])
                .build();
    }

    public static String studentToCsv(Student student) {
        return String.join(",",
                student.getRegNo(),
                student.getFullName(),
                student.getEmail(),
                student.getStatus().toString()
        );
    }

    public static String courseToCsv(Course course) {
        return String.join(",",
                course.getCourseCode().getCode(),
                course.getTitle(),
                String.valueOf(course.getCredits()),
                course.getInstructor(),
                course.getSemester().toString(),
                course.getDepartment()
        );
    }
}
