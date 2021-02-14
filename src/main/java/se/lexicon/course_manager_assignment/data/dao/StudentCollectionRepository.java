package se.lexicon.course_manager_assignment.data.dao;


import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        Student newStudent = new Student(name, email, address);
        students.add(newStudent);
        return newStudent;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        return students.stream()
                .filter(student -> student.getEmail() != null)
                .filter(student -> student.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        Collection<Student> studentsCollection = students.stream()
                .filter(student -> student.getName() != null)
                .filter(student -> student.getName().contains(name))
                .collect(Collectors.toList());
        return (!studentsCollection.isEmpty()) ? studentsCollection : null;
    }

    @Override
    public Student findById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Student> findAll() {
        return (!students.isEmpty()) ? students : null;
    }

    @Override
    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }
}
