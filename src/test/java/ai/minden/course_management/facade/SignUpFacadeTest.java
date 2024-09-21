package ai.minden.course_management.facade;

import ai.minden.course_management.service.SignUpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SignUpFacadeTest {
    private static final String CINDY = "cindy@a.com";
    private static final String ENGLISH = "English";

    @InjectMocks
    private SignUpFacadeImpl signUpFacade;

    @Mock
    private SignUpService signUpService;

    @Test
    public void signUp() {
        signUpFacade.signUp(CINDY, ENGLISH);
        verify(signUpService).signUp(CINDY, ENGLISH);
    }
}
