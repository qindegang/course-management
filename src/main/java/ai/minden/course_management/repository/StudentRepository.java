package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * repository for course
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * find a student by email
     *
     * @param email email of the student
     * @return the student wrapped in an {@link Optional}
     */
    Optional<Student> findByEmail(String email);
}
