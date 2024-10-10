package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.SignUp;
import ai.minden.course_management.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SignUpRepository extends JpaRepository<SignUp, SignUp.Id> {

    /**
     * find classmates of a student's course, if the student does not take this course, no classmates is returned
     *
     * @param student  the student
     * @param course   the course
     * @param pageable pagination information
     * @return a page of classmates found
     */
    @Query(value = "select u.student from SignUp u where u.course=:course and u.student!=:student and exists(select 1 from SignUp u1 where u1.student=:student and u1.course=:course) order by u.student.email")
    Page<Student> findClassMates(@Param("student") Student student, @Param("course") Course course, Pageable pageable);
}
