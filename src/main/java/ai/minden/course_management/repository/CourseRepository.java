package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
