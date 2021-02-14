package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
    }

    //Write your tests here

    public static final String NAME = "Omar Ul";
    public static final String EMAIL = "omar@lexicon.com";
    public static final String ADDRESS = "Stockholm Sweden";

    private Student student;
    private Student resultStudent;
    Collection<Student> students;
    private Collection<Student> resultStudents;

    @BeforeEach
    public void BeforeTest() {
        student = new Student(NAME, EMAIL, ADDRESS);
        students = new HashSet<>();
        students.add(student);
        students.add(new Student());
        students.add(new Student("Mr Ben", "ben@mail.com", "London England"));
        testObject = new StudentCollectionRepository(students);
    }


    @Test
    public void createStudentTest() {
        resultStudent = testObject.createStudent(NAME, EMAIL, ADDRESS);
        assertNotNull(resultStudent);
        assertEquals(NAME, resultStudent.getName());
        assertEquals(EMAIL, resultStudent.getEmail());
        assertEquals(ADDRESS, resultStudent.getAddress());
    }

    @Test
    public void findByEmailIgnoreCaseTest() {
        resultStudent = testObject.findByEmailIgnoreCase(EMAIL);
        assertNotNull(resultStudent);
        assertEquals(1, resultStudent.getId());

        resultStudent = testObject.findByEmailIgnoreCase("emai@mail.com");
        assertNull(resultStudent);
    }

    @Test
    public void findByNameContainsTest() {
        resultStudents = testObject.findByNameContains(NAME);
        assertNotNull(resultStudents);
        assertEquals(1, resultStudents.size());

        resultStudents = testObject.findByNameContains("Ali");
        assertNull(resultStudents);
    }

    @Test
    public void findByIdTest() {
        resultStudent = testObject.findById(2);
        assertNotNull(resultStudent);
        assertEquals(2, resultStudent.getId());

        resultStudent = testObject.findById(11);
        assertNull(resultStudent);
    }

    @Test
    public void findAllTest() {
        resultStudents = testObject.findAll();
        assertNotNull(resultStudents);
        assertEquals(3, resultStudents.size());

        testObject.clear();
        resultStudents = testObject.findAll();
        assertNull(resultStudents);
    }

    @Test
    public void removeStudentTest() {
        assertTrue(testObject.removeStudent(student));
        assertFalse(testObject.removeStudent(student));
    }

    @Test
    public void clearTest() {
        testObject.clear();
        assertNull(testObject.findAll());
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }

}
