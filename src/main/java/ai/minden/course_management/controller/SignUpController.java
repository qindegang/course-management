package ai.minden.course_management.controller;

import ai.minden.course_management.dto.CourseDTO;
import ai.minden.course_management.dto.PageDTO;
import ai.minden.course_management.dto.SignUpRequest;
import ai.minden.course_management.dto.StudentDTO;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import ai.minden.course_management.facade.SignUpFacade;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/signups")
public class SignUpController {
    final SignUpFacade signUpFacade;

    public SignUpController(SignUpFacade signUpFacade) {
        this.signUpFacade = signUpFacade;
    }

    @Operation(summary = "Sign up for a course, idempotent in that duplicate sign-up throws no error")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Validated SignUpRequest signUpRequest)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        try {
            this.signUpFacade.signUp(signUpRequest.getEmail(), signUpRequest.getCourse());
        } catch (InvalidStudentEmailException | InvalidCourseNameException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @Operation(summary = "Cancel sign-up for a course, idempotent in that duplicate cancel throws no error")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelSignUp(@RequestBody @Validated SignUpRequest signUpRequest)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        try {
            this.signUpFacade.cancelSignUp(signUpRequest.getEmail(), signUpRequest.getCourse());
        } catch (InvalidStudentEmailException | InvalidCourseNameException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @Operation(summary = "Find all courses a student takes")
    @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CourseDTO> findCourses(@RequestParam final String email) throws InvalidStudentEmailException {
        try {
            return this.signUpFacade.findCourses(email);
        } catch (InvalidStudentEmailException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @Operation(summary = "Find classmates of a student's course, if the student does not take this course, no classmates is returned")
    @GetMapping(value = "classmates", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDTO<StudentDTO> findClassMates(@RequestParam final String email,
                                              @RequestParam final String course,
                                              @RequestParam(required = false, defaultValue = "0") final int pageNumber,
                                              @RequestParam(required = false, defaultValue = "10") final int pageSize)
            throws InvalidStudentEmailException, InvalidCourseNameException {
        try {
            return this.signUpFacade.findClassMates(email, course, PageRequest.of(pageNumber, pageSize));
        } catch (InvalidStudentEmailException | InvalidCourseNameException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
