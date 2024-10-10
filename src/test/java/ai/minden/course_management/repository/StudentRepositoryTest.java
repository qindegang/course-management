package ai.minden.course_management.repository;

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
public class StudentRepositoryTest {

    private static final String ALICE = "alice@a.com";
    private Student studentAlice;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SignUpRepository signUpRepository;

    @BeforeEach
    public void setUp() {
        studentAlice = new Student(ALICE);
        studentRepository.save(studentAlice);
    }

    @Test
    public void findByEmail_success() {
        Optional<Student> student = studentRepository.findByEmail(ALICE);
        assertThat(student).contains(studentAlice);
    }

    @Test
    public void findByEmail_not_found() {
        Optional<Student> student = studentRepository.findByEmail("x@a.com");
        assertThat(student).isEmpty();
    }

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
    }
}
