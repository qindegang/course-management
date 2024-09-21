package ai.minden.course_management.service;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.repository.CourseRepository;
import ai.minden.course_management.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SignUpServiceImpl implements SignUpService {
    final StudentRepository studentRepository;
    final CourseRepository courseRepository;

    public SignUpServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void signUp(String email, String courseName)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        final Student student = this.findStudent(email);
        final Course course = this.findCourse(courseName);
        student.signUp(course);
    }

    @Override
    public void cancelSignUp(String email, String courseName)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        final Student student = this.findStudent(email);
        final Course course = this.findCourse(courseName);
        student.cancelSignUp(course);
    }

    private Course findCourse(String courseName) {
        return this.courseRepository
                .findByName(courseName)
                .orElseThrow(() -> new InvalidCourseNameException(courseName));
    }

    private Student findStudent(String email) {
        return this.studentRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidStudentEmailException(email));
    }
}
