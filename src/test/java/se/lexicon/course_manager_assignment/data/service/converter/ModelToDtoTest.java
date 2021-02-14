package se.lexicon.course_manager_assignment.data.service.converter;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ModelToDto.class})
public class ModelToDtoTest {

    @Autowired
    private Converters testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
    }

    //write your tests here

    public static final String NAME = "Omar Ul";
    public static final String EMAIL = "omar@lexicon.com";
    public static final String ADDRESS = "Stockholm Sweden";

    public static final String COURSE_NAME = "Java";
    public static final LocalDate START_DATE = LocalDate.parse("2021-05-15");
    public static final int WEEK_DURATION = 16;

    private Course course;
    Collection<Course> courses;
    CourseView courseToCourseViewResult;
    Collection<CourseView> coursesToViewResult;

    private Student student;
    Collection<Student> students;
    StudentView studentToStudentViewResult;
    Collection<StudentView> studentsToViewResult;

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
    }

    @After
    public void afterTest() {
        students.clear();
        courses.clear();
        StudentSequencer.setStudentSequencer(0);
        CourseSequencer.setCourseSequencer(0);
    }

    @Test
    public void studentToStudentViewTest() {
        studentToStudentViewResult = testObject.studentToStudentView(student);
        assertNotNull(studentToStudentViewResult);
        assertEquals(NAME, studentToStudentViewResult.getName());
    }

    @Test
    public void courseToCourseViewTest() {
        courseToCourseViewResult = testObject.courseToCourseView(course);
        assertNotNull(courseToCourseViewResult);
        assertEquals(COURSE_NAME, courseToCourseViewResult.getCourseName());
        assertNotNull(courseToCourseViewResult.getStudents());
        assertEquals(3, courseToCourseViewResult.getStudents().size());
    }

    @Test
    public void coursesToCourseViews() {
        coursesToViewResult = testObject.coursesToCourseViews(courses);
        assertNotNull(coursesToViewResult);
        assertEquals(3,coursesToViewResult.size());
    }

    @Test
    public void studentsToStudentViewsTest(){
        studentsToViewResult=testObject.studentsToStudentViews(students);
        assertNotNull(studentsToViewResult);
        assertEquals(3,studentsToViewResult.size());
    }
}
