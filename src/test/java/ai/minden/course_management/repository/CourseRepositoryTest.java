package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.SignUp;
import ai.minden.course_management.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase
public class CourseRepositoryTest {

    private static final String ENGLISH = "english";
    private static final String PHYSICS = "physics";
    private static final String ALICE = "alice@a.com";

    private Student studentAlice;
    private Course courseEnglish;
    private Course coursePhysics;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SignUpRepository signUpRepository;

    @BeforeEach
    public void setUp() {
        studentAlice = new Student(ALICE);
        courseEnglish = new Course(ENGLISH);
        coursePhysics = new Course(PHYSICS);

        studentRepository.save(studentAlice);
        courseRepository.save(courseEnglish);
        courseRepository.save(coursePhysics);
    }

    @Test
    public void findByName_success() {
        Optional<Course> course = courseRepository.findByName(ENGLISH);
        assertThat(course).contains(courseEnglish);
    }

    @Test
    public void findByName_not_found() {
        Optional<Course> course = courseRepository.findByName("abc");
        assertThat(course).isEmpty();
    }

    @Test
    public void findCourses_empty() {
        assertThat(courseRepository.findCourses(studentAlice)).isEmpty();
    }

    @Test
    public void findCourses() {
        signUpRepository.save(new SignUp(studentAlice, courseEnglish));
        signUpRepository.save(new SignUp(studentAlice, coursePhysics));
        assertThat(courseRepository.findCourses(studentAlice)).containsExactly(courseEnglish, coursePhysics);
    }

    @AfterEach
    public void tearDown() {
        signUpRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }
}
