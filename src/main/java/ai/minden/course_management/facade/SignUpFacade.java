package ai.minden.course_management.facade;

import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;

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
}
