package ai.minden.course_management.service;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.SignUp;
import ai.minden.course_management.entity.Student;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.repository.CourseRepository;
import ai.minden.course_management.repository.SignUpRepository;
import ai.minden.course_management.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
    private static final String CINDY = "cindy@a.com";
    private static final String ENGLISH = "English";
    private static final Pageable PAGEABLE = PageRequest.of(0, 10);

    private Course courseEnglish;
    private Student studentCindy;

    @InjectMocks
    private SignUpServiceImpl signUpService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SignUpRepository signUpRepository;


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
        verify(this.signUpRepository).save(new SignUp(studentCindy, courseEnglish));
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

    @Test
    public void cancelSignUp_success() {
        final var signUp = mock(SignUp.class);
        when(this.courseRepository.findByName(ENGLISH)).thenReturn(Optional.of(courseEnglish));
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));
        when(this.signUpRepository.findById(new SignUp.Id(studentCindy.getId(), courseEnglish.getId()))).thenReturn(Optional.of(signUp));

        signUpService.cancelSignUp(CINDY, ENGLISH);
        verify(this.signUpRepository).delete(signUp);
    }

    @Test
    public void cancelSignUp_not_exist() {
        final var signUp = mock(SignUp.class);
        when(this.courseRepository.findByName(ENGLISH)).thenReturn(Optional.of(courseEnglish));
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));
        when(this.signUpRepository.findById(new SignUp.Id(studentCindy.getId(), courseEnglish.getId()))).thenReturn(Optional.empty());

        signUpService.cancelSignUp(CINDY, ENGLISH);
        verify(this.signUpRepository, never()).delete(signUp);
    }

    @Test
    public void cancelSignUp_invalidEmail() {
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            signUpService.cancelSignUp(CINDY, ENGLISH);
        }).isInstanceOf(InvalidStudentEmailException.class);
    }

    @Test
    public void cancelSignUp_invalidCourse() {
        when(this.courseRepository.findByName(ENGLISH)).thenReturn(Optional.empty());
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));

        assertThatThrownBy(() -> {
            signUpService.cancelSignUp(CINDY, ENGLISH);
        }).isInstanceOf(InvalidCourseNameException.class);
    }

    @Test
    public void findCourses_success() {
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));
        when(this.courseRepository.findCourses(studentCindy)).thenReturn(List.of(courseEnglish));
        assertThat(signUpService.findCourses(CINDY)).containsExactlyInAnyOrder(courseEnglish);
    }

    @Test
    public void findCourses_invalidEmail() {
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            signUpService.findCourses(CINDY);
        }).isInstanceOf(InvalidStudentEmailException.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findClassMates_success() {
        var result = mock(Page.class);
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));
        when(this.courseRepository.findByName(ENGLISH)).thenReturn(Optional.of(courseEnglish));
        when(this.signUpRepository.findClassMates(studentCindy, courseEnglish, PAGEABLE)).thenReturn(result);

        assertThat(signUpService.findClassMates(CINDY, ENGLISH, PAGEABLE)).isSameAs(result);
    }

    @Test
    public void findClassMates_invalidEmail() {
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            signUpService.findClassMates(CINDY, ENGLISH, PAGEABLE);
        }).isInstanceOf(InvalidStudentEmailException.class);
    }

    @Test
    public void findClassMates_invalidCourse() {
        when(this.courseRepository.findByName(ENGLISH)).thenReturn(Optional.empty());
        when(this.studentRepository.findByEmail(CINDY)).thenReturn(Optional.of(studentCindy));

        assertThatThrownBy(() -> {
            signUpService.findClassMates(CINDY, ENGLISH, PAGEABLE);
        }).isInstanceOf(InvalidCourseNameException.class);
    }

}
