package se.lexicon.course_manager_assignment.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ModelToDto implements Converters {

    @Override
    public StudentView studentToStudentView(Student student) {
        return new StudentView(student.getId(),
                student.getName(),student.getEmail(),student.getAddress());
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        List<StudentView> StudentViewArray=new ArrayList<>();
        for(Student s: course.getStudents()){
            StudentViewArray.add(studentToStudentView(s));
        }
        return new CourseView(course.getId(),course.getCourseName(),
                course.getStartDate(),
                course.getWeekDuration(),
                StudentViewArray);
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {
        List<CourseView> courseViewList = new ArrayList<>();
        for(Course c : courses){
            courseViewList.add(courseToCourseView(c));
        }
        return courseViewList;
    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        List<StudentView> studentViewList = new ArrayList<>();
        for (Student s: students){
            studentViewList.add(studentToStudentView(s));
        }
        return studentViewList;
    }
}
