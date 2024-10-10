package ai.minden.course_management.service;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.SignUp;
import ai.minden.course_management.entity.Student;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.repository.CourseRepository;
import ai.minden.course_management.repository.SignUpRepository;
import ai.minden.course_management.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SignUpServiceImpl implements SignUpService {
    final StudentRepository studentRepository;
    final CourseRepository courseRepository;
    final SignUpRepository signUpRepository;

    public SignUpServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, SignUpRepository signUpRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.signUpRepository = signUpRepository;
    }

    @Override
    public void signUp(String email, String courseName)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        final Student student = this.findStudent(email);
        final Course course = this.findCourse(courseName);
        final SignUp signUp = new SignUp(student, course);
        signUpRepository.save(signUp);
    }

    @Override
    public void cancelSignUp(String email, String courseName)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        final Student student = this.findStudent(email);
        final Course course = this.findCourse(courseName);
        signUpRepository.findById(new SignUp.Id(student.getId(), course.getId()))
                .ifPresent(signUpRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findCourses(String email) throws InvalidStudentEmailException {
        return this.courseRepository.findCourses(this.findStudent(email));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Student> findClassMates(String email, String courseName, Pageable pageable)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        final Student student = this.findStudent(email);
        final Course course = this.findCourse(courseName);
        return this.signUpRepository.findClassMates(student, course, pageable);
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
