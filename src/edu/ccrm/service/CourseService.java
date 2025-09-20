package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseService implements Searchable<Course> {

    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Optional<Course> findCourseByCode(String code) {
        return courses.stream()
                .filter(c -> c.getCourseCode().getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    @Override
    public List<Course> search(Predicate<Course> filter) {
        return courses.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public List<Course> filterByInstructor(String instructor) {
        return search(c -> c.getInstructor().equalsIgnoreCase(instructor));
    }

    public List<Course> filterByDepartment(String department) {
        return search(c -> c.getDepartment().equalsIgnoreCase(department));
    }

    public List<Course> filterBySemester(Semester semester) {
        return search(c -> c.getSemester() == semester);
    }
    
    public void loadCourses(List<Course> courses) {
        this.courses.clear();
        this.courses.addAll(courses);
    }
}
