package ai.minden.course_management.service;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.repository.CourseRepository;
import ai.minden.course_management.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCourses(String email) throws InvalidStudentEmailException {
        return this.findStudent(email).getCourses();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Student> findClassMates(String email, String courseName, Pageable pageable)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        final Student student = this.findStudent(email);
        final Course course = this.findCourse(courseName);
        return this.studentRepository.findClassMates(student, course, pageable);
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
