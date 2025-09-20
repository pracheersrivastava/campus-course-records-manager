package edu.ccrm.util;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;

import java.util.Comparator;

/**
 * A utility class that holds various comparators for sorting domain objects.
 *
 * DEMONSTRATES:
 * - Lambda Expressions: For creating concise comparator instances.
 * - Static fields for reusable comparator objects.
 */
public class Comparators {

    /**
     * A comparator for sorting students by their full name.
     * DEMONSTRATES: Lambda expression for a functional interface.
     */
    public static final Comparator<Student> STUDENT_NAME_COMPARATOR =
            (s1, s2) -> s1.getFullName().compareToIgnoreCase(s2.getFullName());

    /**
     * A comparator for sorting courses by their title.
     */
    public static final Comparator<Course> COURSE_TITLE_COMPARATOR =
            Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER);

    /**
     * A comparator for sorting courses by their department, then by title.
     * DEMONSTRATES: Comparator chaining.
     */
    public static final Comparator<Course> COURSE_DEPT_AND_TITLE_COMPARATOR =
            Comparator.comparing(Course::getDepartment)
                      .thenComparing(Course::getTitle);
}
