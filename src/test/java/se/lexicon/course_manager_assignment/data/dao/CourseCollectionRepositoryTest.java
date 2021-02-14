package se.lexicon.course_manager_assignment.data.dao;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    @Autowired
    private CourseDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }


    //Write your tests here

    public static final String NAME = "Omar Ul";
    public static final String EMAIL = "omar@lexicon.com";
    public static final String ADDRESS = "Stockholm Sweden";

    public static final String COURSE_NAME = "Java";
    public static final LocalDate START_DATE = LocalDate.parse("2021-05-15");
    public static final int WEEK_DURATION = 16;

    private Course course;
    private Course resultCourse;
    private Student student;
    Collection<Student> students;
    Collection<Course> courses;
    Collection<Course> resultCourses;

    @BeforeEach
    @DisplayName("Test")
    public void BeforeTest() {
        student = new Student(NAME, EMAIL, ADDRESS);
        students = new HashSet<>();
        students.add(student);
        students.add(new Student());
        students.add(new Student("Mr Ben", "ben@mail.com", "London England"));
        course = new Course(COURSE_NAME, START_DATE, WEEK_DURATION, students);
        courses = new HashSet<>();
        courses.add(course);
        courses.add(new Course());
        courses.add(new Course("HTML", LocalDate.parse("2021-09-21"), 13));
        testObject = new CourseCollectionRepository(courses);
    }

    @Test
    public void createCourseTest() {
        resultCourse = testObject.createCourse(COURSE_NAME, START_DATE, WEEK_DURATION);
        assertNotNull(resultCourse);
        assertEquals(COURSE_NAME, resultCourse.getCourseName());
        assertEquals(START_DATE, resultCourse.getStartDate());
        assertEquals(WEEK_DURATION, resultCourse.getWeekDuration());
    }

    @Test
    public void findByIdTest() {
        resultCourse = testObject.findById(2);
        assertNotNull(resultCourse);
        assertEquals(2, resultCourse.getId());

        resultCourse = testObject.findById(11);
        assertNull(resultCourse);
    }

    @Test
    public void findByNameContainsTest() {
        resultCourses = testObject.findByNameContains(COURSE_NAME);
        assertNotNull(resultCourses);
        assertEquals(1, resultCourses.size());

        resultCourses = testObject.findByNameContains("PHP");
        assertNull(resultCourses);
    }

    @Test
    public void findByDateBeforeTest() {
        resultCourses = testObject.findByDateBefore(LocalDate.parse("2021-11-01"));
        assertFalse(resultCourses.isEmpty());

        resultCourses = testObject.findByDateBefore(LocalDate.parse("2021-03-01"));
        assertNull(resultCourses);
    }

    @Test
    public void findByDateAfterTest() {
        resultCourses = testObject.findByDateAfter(LocalDate.parse("2021-09-01"));
        assertFalse(resultCourses.isEmpty());

        resultCourses = testObject.findByDateAfter(LocalDate.parse("2022-03-01"));
        assertNull(resultCourses);
    }

    @Test
    public void findAllTest() {
        resultCourses = testObject.findAll();
        assertNotNull(resultCourses);
        assertEquals(3, resultCourses.size());
    }

    @Test
    public void findByStudentIdTest() {
       resultCourses = testObject.findByStudentId(2);
       assertNotNull(resultCourses);
        assertEquals(1, resultCourses.size());

        resultCourses = testObject.findByStudentId(11);
        assertNull(resultCourses);
    }

    @Test
    public void removeCourse() {
        assertTrue(testObject.removeCourse(course));
        assertFalse(testObject.removeCourse(course));
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        CourseSequencer.setCourseSequencer(0);
        StudentSequencer.setStudentSequencer(0);
    }
}
