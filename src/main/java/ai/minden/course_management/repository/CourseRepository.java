package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * repository for course
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * find a course by its name
     *
     * @param name name of the course
     * @return the course wrapped in an {@link Optional}
     */
    Optional<Course> findByName(String name);

    /**
     * find a student's courses
     *
     * @param student the student
     * @return the courses taken by this student
     */
    @Query(value = "select u.course from Student s join s.signUps u where s=:student order by u.course.name")
    List<Course> findCourses(@Param("student") Student student);

}
