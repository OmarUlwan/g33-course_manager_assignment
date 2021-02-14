package se.lexicon.course_manager_assignment.data.service.course;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.data.service.student.StudentService;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {CourseManager.class, CourseCollectionRepository.class, ModelToDto.class, StudentCollectionRepository.class})
public class CourseManagerTest {

    @Autowired
    private CourseService testObject;

    @Autowired
    private CourseDao courseDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(courseDao);
    }

    //Write your tests here

    public static final String NAME = "Omar Ul";
    public static final String EMAIL = "omar@lexicon.com";
    public static final String ADDRESS = "Stockholm Sweden";

    public static final String COURSE_NAME = "Java";
    public static final LocalDate START_DATE = LocalDate.parse("2021-05-15");
    public static final int WEEK_DURATION = 16;

    Course course;
    Student student;
    Student student2;
    Collection<Student> students;

    List<CourseView> resultCourseViewArray;
    CourseView resultCourseView;

    @BeforeEach
    @DisplayName("Test")
    public void BeforeTest() {
        student = testObject.getStudentDao().createStudent(NAME, EMAIL, ADDRESS);
        student2 = testObject.getStudentDao().createStudent(NAME, EMAIL, ADDRESS);
        students = new HashSet<>();
        students.add(student);
        course = courseDao.createCourse(COURSE_NAME, START_DATE, WEEK_DURATION);
        course.setStudents(students);
    }

    @Test
    public void createTest() {
        testObject.create(new CreateCourseForm(1, "CreatTest", START_DATE, WEEK_DURATION));
        assertNotNull(courseDao.findAll());
        assertEquals(2, courseDao.findAll().size());
    }

    @Test
    public void updateTest() {
        assertNull(courseDao.findByNameContains("courseNameUpdate"));

        testObject.update(new UpdateCourseForm(1, "courseNameUpdate", START_DATE, WEEK_DURATION));
        assertNotNull(courseDao.findAll());
        assertEquals(1, courseDao.findAll().size());
        assertNotNull(courseDao.findByNameContains("courseNameUpdate"));
    }

    @Test
    public void searchByCourseNameTest() {
        resultCourseViewArray = testObject.searchByCourseName(COURSE_NAME);

        assertNotNull(resultCourseViewArray);
        assertEquals(1, resultCourseViewArray.size());
        resultCourseView = resultCourseViewArray.stream()
                .filter(courseView -> courseView.getCourseName().equalsIgnoreCase(COURSE_NAME))
                .findFirst()
                .orElse(null);
        assertTrue(resultCourseView.getCourseName().equalsIgnoreCase(COURSE_NAME));
    }

    @Test
    public void searchByDateBeforeTest() {
        LocalDate end = LocalDate.parse("2022-06-20");
        resultCourseViewArray = testObject.searchByDateBefore(end);

        assertNotNull(resultCourseViewArray);
        assertEquals(1, resultCourseViewArray.size());

        end = LocalDate.parse("2021-06-20");
        resultCourseViewArray = testObject.searchByDateBefore(end);

        assertNull(resultCourseViewArray);


    }

    @Test
    public void searchByDateAfterTest() {
        LocalDate end = LocalDate.parse("2021-06-20");
        resultCourseViewArray = testObject.searchByDateAfter(end);

        assertNotNull(resultCourseViewArray);
        assertEquals(1, resultCourseViewArray.size());

        end = LocalDate.parse("2022-06-20");
        resultCourseViewArray = testObject.searchByDateAfter(end);

        assertNull(resultCourseViewArray);
    }

    @Test
    public void addStudentToCourseTest() {
        boolean isTrue = testObject.addStudentToCourse(course.getId(), student2.getId());
        assertTrue(isTrue);

        boolean isFalse = testObject.addStudentToCourse(course.getId(), student2.getId());
        assertFalse(isFalse);

        isFalse = testObject.addStudentToCourse(5, student2.getId());
        assertFalse(isFalse);

    }

    @Test
    public void removeStudentFromCourseTest(){
        boolean isTrue= testObject.removeStudentFromCourse(course.getId(),student.getId());
        assertTrue(isTrue);

        boolean isFalse= testObject.removeStudentFromCourse(course.getId(),student.getId());
        assertFalse(isFalse);
    }

    @Test
    public void findByIdTest(){
        resultCourseView = testObject.findById(1);
        assertNotNull(resultCourseView);

        resultCourseView = testObject.findById(5);
        assertNull(resultCourseView);

    }

    @Test
    public void findAllTest(){
        resultCourseViewArray = testObject.findAll();
        assertNotNull(resultCourseViewArray);
        assertEquals(1,resultCourseViewArray.size());
    }

    @Test
    public void findByStudentIdTest(){
        resultCourseViewArray = testObject.findByStudentId(1);

        assertNotNull(resultCourseViewArray);
        assertEquals(1,resultCourseViewArray.size());
    }

    @Test
    public void deleteCourseTest(){
        boolean isTrue = testObject.deleteCourse(1);
        assertTrue(isTrue);

        boolean isFalse = testObject.deleteCourse(1);
        assertFalse(isFalse);
    }

    @AfterEach
    void tearDown() {
        courseDao.clear();
        StudentSequencer.setStudentSequencer(0);
        CourseSequencer.setCourseSequencer(0);
    }
}
