package se.lexicon.course_manager_assignment.data.service.student;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {StudentManager.class, CourseCollectionRepository.class, StudentCollectionRepository.class, ModelToDto.class})
public class StudentManagerTest {

    @Autowired
    private StudentService testObject;
    @Autowired
    private StudentDao studentDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(studentDao);
    }

    //Write your tests here

    public static final String NAME = "Omar Ul";
    public static final String EMAIL = "omar@lexicon.com";
    public static final String ADDRESS = "Stockholm Sweden";

    private Student student;
    private StudentView result;
    private List<StudentView> resultListStudentView;

    @BeforeEach
    public void beforeTest() {
        student = studentDao.createStudent(NAME, EMAIL, ADDRESS);
        studentDao.createStudent("Alaa", "alaa@mail.com", "Malmo Sweden");
        Course course = testObject.getCourseDao().createCourse("courseName", LocalDate.parse("2021-01-01"),9);
        course.getStudents().add(student);
        }

    @Test
    public void createTest() {
        testObject.create(new CreateStudentForm(3, NAME, EMAIL, ADDRESS));
        assertNotNull(studentDao.findAll());
        assertEquals(3, studentDao.findAll().size());
    }

    @Test
    public void updateTest() {

        assertNull(studentDao.findByNameContains("NAME"));

        testObject.update(new UpdateStudentForm(1, "NAME", "EMAIL", "ADDRESS"));
        assertNotNull(studentDao.findAll());
        assertEquals(2, studentDao.findAll().size());

        assertNotNull(studentDao.findByNameContains("NAME"));
    }

    @Test
    public void findByIdTest() {
        result = testObject.findById(2);
        assertNotNull(result);
        assertEquals("Alaa", result.getName());
    }

    @Test
    public void searchByEmailTest(){
        result = testObject.searchByEmail(EMAIL);
        assertNotNull(result);
        assertEquals(EMAIL,result.getEmail());
    }

    @Test
    public void searchByNameTest(){
        student = studentDao.createStudent(NAME, EMAIL, ADDRESS);

        resultListStudentView = testObject.searchByName("Alaa");
        assertNotNull(resultListStudentView);
        assertEquals(1,resultListStudentView.size());

        resultListStudentView = testObject.searchByName(NAME);
        assertNotNull(resultListStudentView);
        assertEquals(2,resultListStudentView.size());
    }

    @Test
    public void findAllTest(){
        resultListStudentView=testObject.findAll();
        assertNotNull(resultListStudentView);
        assertEquals(2,resultListStudentView.size());
    }

    @Test
    public void deleteStudentTest(){
        boolean isTrue=testObject.deleteStudent(1);
        assertTrue(isTrue);

        boolean isFalse=testObject.deleteStudent(1);
        assertFalse(isFalse);

        isTrue=testObject.deleteStudent(2);
        assertTrue(isTrue);


    }
    @AfterEach
    void tearDown() {
        studentDao.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
