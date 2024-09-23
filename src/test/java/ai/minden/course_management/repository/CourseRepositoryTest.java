package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
public class CourseRepositoryTest {

    private static final String COURSE_NAME = "english";
    private static final Course COURSE = new Course(COURSE_NAME);

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        courseRepository.save(COURSE);
    }

    @Test
    public void findByName_success() {
        Optional<Course> course = courseRepository.findByName(COURSE_NAME);
        assertThat(course).contains(COURSE);
    }

    @Test
    public void findByName_not_found() {
        Optional<Course> course = courseRepository.findByName("abc");
        assertThat(course).isEmpty();
    }

    @AfterEach
    public void tearDown() {
        courseRepository.delete(COURSE);
    }
}
