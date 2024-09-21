package ai.minden.course_management.service;

import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;

/**
 * service for the signup
 */
public interface SignUpService {
    /**
     * sign up for a course, note that no error is thrown if the student has already signed up
     *
     * @param email      email of the student
     * @param courseName name of the course
     * @throws InvalidStudentEmailException if the email is invalid
     * @throws InvalidCourseNameException   if the course name is invalid
     */
    void signUp(String email, String courseName) throws InvalidStudentEmailException, InvalidCourseNameException;
}
