package ai.minden.course_management.exception;

public class InvalidStudentEmailException extends RuntimeException {
    public InvalidStudentEmailException(String email) {
        super(String.format("Student email %s is invalid", email));
    }
}
