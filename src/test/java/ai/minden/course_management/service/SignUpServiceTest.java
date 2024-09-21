package ai.minden.course_management.service;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.repository.CourseRepository;
import ai.minden.course_management.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
    private static final String CINDY = "cindy@a.com";
    private static final String ENGLISH = "English";

    private Course courseEnglish;
    private Student studentCindy;

    @InjectMocks
    private SignUpServiceImpl signUpService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        courseEnglish = new Course(ENGLISH);
        studentCindy = new Student(CINDY);
    }

    @Test
    public void signUp_success() {
        when(this.courseRepository.findByName(ENGLISH)).thenReturn(Optional.of(courseEnglish));
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));

        signUpService.signUp(CINDY, ENGLISH);
        assertThat(studentCindy.getCourses()).contains(courseEnglish);
    }

    @Test
    public void signUp_invalidEmail() {
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            signUpService.signUp(CINDY, ENGLISH);
        }).isInstanceOf(InvalidStudentEmailException.class);
    }

    @Test
    public void signUp_invalidCourse() {
        when(this.courseRepository.findByName(ENGLISH)).thenReturn(Optional.empty());
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));

        assertThatThrownBy(() -> {
            signUpService.signUp(CINDY, ENGLISH);
        }).isInstanceOf(InvalidCourseNameException.class);
    }
}
