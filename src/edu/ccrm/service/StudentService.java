package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentService implements Searchable<Student> {

    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public Optional<Student> findStudentByRegNo(String regNo) {
        return students.stream()
                .filter(s -> s.getRegNo().equalsIgnoreCase(regNo))
                .findFirst();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public List<Student> search(Predicate<Student> filter) {
        return students.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
    
    public void loadStudents(List<Student> students) {
        this.students.clear();
        this.students.addAll(students);
    }
}
