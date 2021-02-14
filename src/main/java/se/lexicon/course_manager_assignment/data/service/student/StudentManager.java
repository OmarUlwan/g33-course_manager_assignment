package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;
import java.util.List;

@Service
public class StudentManager implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final Converters converters;

    @Autowired
    public StudentManager(StudentDao studentDao, CourseDao courseDao, Converters converters) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.converters = converters;
    }

    // For test in class StudentManagerTest
    public CourseDao getCourseDao() {
        return courseDao;
    }

    @Override
    public StudentView create(CreateStudentForm form) {
        Student creatStudentForm = studentDao.createStudent(form.getName(),
                form.getEmail(), form.getAddress());
        return converters.studentToStudentView(creatStudentForm);
    }

    @Override
    public StudentView update(UpdateStudentForm form) {
        Student findStudent = studentDao.findById(form.getId());
        if (findStudent != null) {
            findStudent.setName(form.getName());
            findStudent.setEmail(form.getEmail());
            findStudent.setAddress(form.getEmail());
        }
        return (findStudent != null) ? converters.studentToStudentView(findStudent) : null;
    }

    @Override
    public StudentView findById(int id) {
        Student student = studentDao.findById(id);
        return (student != null) ? converters.studentToStudentView(student) : null;
    }

    @Override
    public StudentView searchByEmail(String email) {
        Student student = studentDao.findByEmailIgnoreCase(email);
        return (student != null) ? converters.studentToStudentView(student) : null;
    }

    @Override
    public List<StudentView> searchByName(String name) {
        Collection<Student> studentsArray = studentDao.findByNameContains(name);
        return (!studentsArray.isEmpty()) ? converters.studentsToStudentViews(studentsArray) : null;
    }

    @Override
    public List<StudentView> findAll() {
        Collection<Student> students = studentDao.findAll();
        return (students != null) ? converters.studentsToStudentViews(students) : null;
    }

    @Override
    public boolean deleteStudent(int id) {
        Collection<Course> courses = courseDao.findByStudentId(id);
        Collection<Student> students;

        if (courses != null) {
            for (Course c : courses) {
                students = c.getStudents();
                for (Student s : students) if (s.getId() == id) c.getStudents().remove(s);
            }
        }
        return studentDao.removeStudent(studentDao.findById(id));
    }
}
