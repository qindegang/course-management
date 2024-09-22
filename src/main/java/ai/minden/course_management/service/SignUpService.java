package ai.minden.course_management.service;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import ai.minden.course_management.exception.InvalidCourseNameException;
import ai.minden.course_management.exception.InvalidStudentEmailException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * service for the signup
 */
public interface SignUpService {
    /**
     * sign up for a course, idempotent in that duplicate sign-up throws no error
     *
     * @param email      email of the student
     * @param courseName name of the course
     * @throws InvalidStudentEmailException if the email is invalid
     * @throws InvalidCourseNameException   if the course name is invalid
     */
    void signUp(String email, String courseName) throws InvalidStudentEmailException, InvalidCourseNameException;

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
     * find courses taken by the student
     *
     * @param email email of the student
     * @return the courses taken by the student
     * @throws InvalidStudentEmailException if the email is invalid
     */
    Set<Course> findCourses(String email) throws InvalidStudentEmailException;

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
    Page<Student> findClassMates(String email, String courseName, Pageable pageable) throws InvalidStudentEmailException, InvalidCourseNameException;
}
