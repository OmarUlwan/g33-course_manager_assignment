package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        Course newCourse = courseDao.createCourse(form.getCourseName(),
                form.getStartDate(), form.getWeekDuration());
        return converters.courseToCourseView(newCourse);
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        Course findCourses = courseDao.findById(form.getId());
        if (findCourses != null) {
            findCourses.setCourseName(form.getCourseName());
            findCourses.setStartDate(form.getStartDate());
            findCourses.setWeekDuration(form.getWeekDuration());
        }
        return (findCourses != null) ? converters.courseToCourseView(findCourses) : null;
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        Collection<Course> searchByCourseName = courseDao.findByNameContains(courseName);
        return (searchByCourseName != null) ? converters.coursesToCourseViews(searchByCourseName) : null;
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        Collection<Course> searchByDateBefore = courseDao.findByDateBefore(end);
        return (searchByDateBefore != null) ? converters.coursesToCourseViews(searchByDateBefore) : null;
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        Collection<Course> searchByDateAfter = courseDao.findByDateAfter(start);
        return (searchByDateAfter != null) ? converters.coursesToCourseViews(searchByDateAfter) : null;
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        Student foundStudent = studentDao.findById(studentId);
        Course foundCourse = courseDao.findById(courseId);
        if (foundCourse != null && foundStudent != null) return foundCourse.getStudents().add(foundStudent);
        return false;
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        Student removeStudent = studentDao.findById(studentId);
        Course removeStudentFromCourse = courseDao.findById(courseId);
        if (removeStudentFromCourse != null && removeStudent != null)
            for (Student s : removeStudentFromCourse.getStudents())
                if (s.getId() == studentId) return removeStudentFromCourse.getStudents().remove(s);
        return false;
    }

    @Override
    public CourseView findById(int id) {
        Course findCourse = courseDao.findById(id);
        return (findCourse != null) ? converters.courseToCourseView(findCourse) : null;
    }

    @Override
    public List<CourseView> findAll() {
        Collection<Course> allCourse = courseDao.findAll();
        return (allCourse != null) ? converters.coursesToCourseViews(allCourse) : null;
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        Collection<Course> findByStudentID = courseDao.findByStudentId(studentId);
        return (findByStudentID != null) ? converters.coursesToCourseViews(findByStudentID) : null;
    }

    @Override
    public boolean deleteCourse(int id) {
        Course deleteCourse = courseDao.findById(id);
        return courseDao.removeCourse(deleteCourse);
    }
}
