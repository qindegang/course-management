package ai.minden.course_management.facade;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.service.SignUpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignUpFacadeTest {
    private static final String CINDY = "cindy@a.com";
    private static final String ENGLISH = "English";
    private static final Course COURSE_ENGLISH = new Course(ENGLISH);

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
}
