package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * find classmates of a student's course, if the student does not take this course, no classmates is returned
     *
     * @param student  the student
     * @param course   the course
     * @param pageable pagination information
     * @return a page of classmates found
     */
    @Query(value = "select s from Course c join c.students s where c=:course and s!=:student and :student member of c.students order by s.email")
    Page<Student> findClassMates(@Param("student") Student student, @Param("course") Course course, Pageable pageable);
}
