package se.lexicon.course_manager_assignment.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CourseTest {

    public static final String NAME = "Omar Ul";
    public static final String EMAIL = "omar@lexicon.com";
    public static final String ADDRESS = "Stockholm Sweden";

    public static final String COURSE_NAME = "Java";
    public static final LocalDate START_DATE = LocalDate.parse("2021-05-15");
    public static final int WEEK_DURATION = 16;

    private Course course;
    private Student student;
    private Collection<Student> students;

    @Before
    @DisplayName("Test")
    public void BeforeTest() {
        student = new Student(NAME, EMAIL, ADDRESS);
        students = new HashSet<>();
        students.add(student);
        course = new Course(COURSE_NAME, START_DATE, WEEK_DURATION, students);
    }

    @After
    public void afterTest() {
        students.clear();
        StudentSequencer.setStudentSequencer(0);
        CourseSequencer.setCourseSequencer(0);
    }

    @Test
    @DisplayName("Course constructor && GetterSetter")
    public void courseConstructorAndGetterSetterTest() {
        new Course(COURSE_NAME,START_DATE,WEEK_DURATION);
        assertEquals(1, course.getId());
        assertEquals(COURSE_NAME, course.getCourseName());
        assertEquals(START_DATE, course.getStartDate());
        assertEquals(WEEK_DURATION, course.getWeekDuration());
        assertEquals(students, course.getStudents());

        course.setCourseName("courseNameTest");
        course.setStartDate(LocalDate.parse("2001-01-01"));
        course.setWeekDuration(9);
        course.setStudents(Collections.emptyList());

        assertEquals(1, course.getId());
        assertEquals("courseNameTest", course.getCourseName());
        assertEquals(LocalDate.parse("2001-01-01"), course.getStartDate());
        assertEquals(9, course.getWeekDuration());
        assertTrue(course.getStudents().isEmpty());

        assertEquals(3, new Course().getId());
    }

    @Test
    @DisplayName("Add student to Collection<student> students")
    public void enrollStudentTest() {
        assertFalse(course.enrollStudent(new Student()) && course.enrollStudent(student));
        assertTrue(course.enrollStudent(new Student(NAME, EMAIL, ADDRESS)));
    }

    @Test
    @DisplayName("Remove student from Collection<student> students")
    public void unEnrollStudentTest() {
        assertFalse(course.unEnrollStudent(new Student(NAME, EMAIL, ADDRESS)));
        assertTrue(course.unEnrollStudent(student));
        assertFalse(course.unEnrollStudent(student));
    }

    @Test
    public void equalsTest() {
        Object[] objects = new Object[]{null, new Object(), new Course()};
        for (Object o : objects) {
            boolean isFalse = course.equals(o);
            assertFalse(isFalse);
        }
        CourseSequencer.setCourseSequencer(0);
        Object object = new Course(COURSE_NAME,START_DATE,WEEK_DURATION,students);
        boolean isTrue = course.equals(object);
        assertTrue(isTrue);
    }


    @Test
    public void hashCodeTest() {
        Object object = course;
        assertEquals(object.hashCode(), course.hashCode());
    }

    @Test
    public void toStringTest() {
        String toString = "Course{id=1, courseName='" + COURSE_NAME
                + "', startDate=" + START_DATE + ", weekDuration=" + WEEK_DURATION
                + ", students=[Student{id=1, name='" + NAME + "', email='" + EMAIL
                + "', address='" + ADDRESS + "'}]}";
        assertEquals(toString, course.toString());
    }
}
