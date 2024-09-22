package ai.minden.course_management.facade;

import ai.minden.course_management.dto.CourseDTO;
import ai.minden.course_management.dto.PageDTO;
import ai.minden.course_management.dto.StudentDTO;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * facade for the signup
 */
public interface SignUpFacade {
    /**
     * sign up for a course, idempotent in that duplicate sign-up throws no error
     *
     * @param email      email of the student
     * @param courseName name of the course
     * @throws InvalidStudentEmailException if the email is invalid
     * @throws InvalidCourseNameException   if the course name is invalid
     */
    void signUp(String email, String courseName)
            throws InvalidStudentEmailException, InvalidCourseNameException;

    /**
     * cancel sign up for a course, idempotent in that duplicate cancel throws no error
     *
     * @param email      email of the student
     * @param courseName name of the course
     * @throws InvalidStudentEmailException if the email is invalid
     * @throws InvalidCourseNameException   if the course name is invalid
     */
    void cancelSignUp(String email, String courseName) throws InvalidStudentEmailException, InvalidCourseNameException;

    /**
     * find courses a student takes
     *
     * @param email email of the student
     * @return the courses taken by the student order by name
     * @throws InvalidStudentEmailException if the email is invalid
     */
    List<CourseDTO> findCourses(String email) throws InvalidStudentEmailException;

    /**
     * find classmates of a student's course, if the student does not take this course, no classmates is returned
     *
     * @param email      email of the student
     * @param courseName name of the course
     * @param pageable   pagination information
     * @return a page of classmates
     * @throws InvalidStudentEmailException if the email is invalid
     * @throws InvalidCourseNameException   if the course name is invalid
     */
    PageDTO<StudentDTO> findClassMates(String email, String courseName, Pageable pageable)
            throws InvalidStudentEmailException, InvalidCourseNameException;
}
