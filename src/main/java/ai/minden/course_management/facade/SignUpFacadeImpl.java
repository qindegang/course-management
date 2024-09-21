package ai.minden.course_management.facade;

import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.service.SignUpService;
import org.springframework.stereotype.Service;

@Service
public class SignUpFacadeImpl implements SignUpFacade {
    private final SignUpService signUpService;

    public SignUpFacadeImpl(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public void signUp(String email, String courseName)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        this.signUpService.signUp(email, courseName);
    }
}
