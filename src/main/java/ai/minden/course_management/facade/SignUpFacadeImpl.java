package ai.minden.course_management.facade;

import ai.minden.course_management.dto.CourseDTO;
import ai.minden.course_management.dto.PageDTO;
import ai.minden.course_management.dto.StudentDTO;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.service.SignUpService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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

    @Override
    public void cancelSignUp(String email, String courseName)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        this.signUpService.cancelSignUp(email, courseName);
    }

    @Override
    public List<CourseDTO> findCourses(String email) throws InvalidStudentEmailException {
        return this.signUpService.findCourses(email)
                .stream()
                .map(c -> new CourseDTO(c.getName()))
                .sorted(Comparator.comparing(CourseDTO::name))
                .toList();
    }

    @Override
    public PageDTO<StudentDTO> findClassMates(String email, String courseName, Pageable pageable)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        final Page<StudentDTO> page = this.signUpService.findClassMates(email, courseName, pageable)
                .map(s -> new StudentDTO(s.getEmail()));
        return new PageDTO<>(page);
    }
}
