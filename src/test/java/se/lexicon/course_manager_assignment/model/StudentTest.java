package se.lexicon.course_manager_assignment.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;

import static org.junit.Assert.*;

public class StudentTest {

    public static final String NAME = "Omar Ul";
    public static final String EMAIL = "omar@lexicon.com";
    public static final String ADDRESS = "Stockholm Sweden";

    private Student student;

    @Before
    public void BeforeTest() {
        student = new Student(NAME, EMAIL, ADDRESS);
    }

    @After
    public void afterTest() {
        StudentSequencer.setStudentSequencer(0);
    }

    @Test
    @DisplayName("Student constructor && GetterSetter")
    public void studentConstructorAndGetterSetterTest() {
        assertEquals(1, student.getId());
        assertEquals(NAME, student.getName());
        assertEquals(EMAIL, student.getEmail());
        assertEquals(ADDRESS, student.getAddress());

        student.setName("NameTest");
        student.setEmail("EmailTest");
        student.setAddress("AddressTest");
        assertEquals(1, student.getId());
        assertEquals("NameTest", student.getName());
        assertEquals("EmailTest", student.getEmail());
        assertEquals("AddressTest", student.getAddress());

        assertEquals(2, new Student().getId());
    }

    @Test
    @DisplayName("Test student.equal")
    public void equalsTest() {
        StudentSequencer.setStudentSequencer(0);
        Object object = new Student(NAME, EMAIL, ADDRESS);
        boolean isTrue = student.equals(object);
        assertTrue(isTrue);

        Object[] objects = new Object[]{null, new Object(), new Student()};
        for (Object o : objects) {
            boolean isFalse = student.equals(o);
            assertFalse(isFalse);
        }
    }

    @Test
    @DisplayName("Test student.hashcode")
    public void hashCodeTest() {
        Object object = student;
        assertEquals(object.hashCode(), student.hashCode());
    }

    @Test
    @DisplayName("Test student.toString")
    public void toStringTest() {
        String toString = "Student{id=1, name='" + NAME
                + "', email='" + EMAIL
                + "', address='" + ADDRESS + "'}";
        assertEquals(toString, student.toString());
    }
}
