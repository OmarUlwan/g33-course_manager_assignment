package se.lexicon.course_manager_assignment.data.dao;


import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


public class CourseCollectionRepository implements CourseDao {

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
        Course newCourse = new Course(courseName, startDate, weekDuration);
        courses.add(newCourse);
        return newCourse;
    }

    @Override
    public Course findById(int id) {
        return courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        Collection<Course> coursesCollection = courses.stream()
                .filter(course -> course.getCourseName() != null)
                .filter(course -> course.getCourseName().contains(name))
                .collect(Collectors.toList());
        return (!coursesCollection.isEmpty()) ? coursesCollection : null;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        Collection<Course> coursesCollection = courses.stream()
                .filter(course -> course.getStartDate() != null)
                .filter(course -> course.getStartDate()
                        .plusWeeks(course.getWeekDuration())
                        .isBefore(end))
                .collect(Collectors.toList());
        return !coursesCollection.isEmpty() ? coursesCollection : null;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        Collection<Course> coursesCollection = courses.stream()
                .filter(course -> course.getStartDate() != null)
                .filter(course -> course.getStartDate()
                        .plusWeeks(course.getWeekDuration())
                        .isAfter(start))
                .collect(Collectors.toList());
        return (!coursesCollection.isEmpty()) ? coursesCollection : null;
    }

    @Override
    public Collection<Course> findAll() {
        return (!courses.isEmpty()) ? courses : null;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        Collection<Course> coursesResult = new ArrayList<>();
        Collection<Student> studentsArray;
        for (Course c : courses) {
            studentsArray = c.getStudents();
            for (Student s : studentsArray) {
                if (s.getId() == studentId) coursesResult.add(c);
            }
        }
        return (!coursesResult.isEmpty()) ? coursesResult : null;
    }

    @Override
    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
