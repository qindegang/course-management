package ai.minden.course_management.exception;

public class InvalidCourseNameException extends RuntimeException {
    public InvalidCourseNameException(String name) {
        super(String.format("Course name %s is invalid", name));
    }
}
