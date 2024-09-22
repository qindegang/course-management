package ai.minden.course_management.facade;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import ai.minden.course_management.service.SignUpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignUpFacadeTest {
    private static final String CINDY = "cindy@a.com";
    private static final Student STUDENT_CINDY = new Student(CINDY);
    private static final String ALICE = "alice@a.com";
    private static final Student STUDENT_ALICE = new Student(ALICE);

    private static final String ENGLISH = "English";
    private static final Course COURSE_ENGLISH = new Course(ENGLISH);
    private static final Pageable PAGEABLE = PageRequest.of(0, 10);

    @InjectMocks
    private SignUpFacadeImpl signUpFacade;

    @Mock
    private SignUpService signUpService;

    @Test
    public void signUp() {
        signUpFacade.signUp(CINDY, ENGLISH);
        verify(signUpService).signUp(CINDY, ENGLISH);
    }

    @Test
    public void cancelSignUp() {
        signUpFacade.cancelSignUp(CINDY, ENGLISH);
        verify(signUpService).cancelSignUp(CINDY, ENGLISH);
    }

    @Test
    public void findCourses() {
        when(signUpService.findCourses(CINDY)).thenReturn(Set.of(COURSE_ENGLISH));

        var courses = signUpFacade.findCourses(CINDY);

        assertThat(courses).hasSize(1);
        assertThat(courses.stream().toList().getFirst().name()).isEqualTo(ENGLISH);
    }

    @Test
    public void findClassMates() {
        when(signUpService.findClassMates(CINDY, ENGLISH, PAGEABLE))
                .thenReturn(new PageImpl<>(List.of(STUDENT_ALICE)));

        var page = signUpFacade.findClassMates(CINDY, ENGLISH, PAGEABLE);

        assertThat(page.content()).hasSize(1);
        assertThat(page.content().getFirst().email()).isEqualTo(ALICE);
    }
}
