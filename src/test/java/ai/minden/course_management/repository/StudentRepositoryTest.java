package ai.minden.course_management.repository;

import ai.minden.course_management.entity.Course;
import ai.minden.course_management.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
public class StudentRepositoryTest {

    private static final String ALICE = "alice@a.com";
    private static final String BOB = "bob@a.com";
    private static final String CINDY = "cindy@a.com";
    private static final String ENGLISH = "English";

    private Course courseEnglish;
    private Student studentAlice;
    private Student studentBob;
    private Student studentCindy;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        studentAlice = new Student(ALICE);
        studentBob = new Student(BOB);
        studentCindy = new Student(CINDY);
        courseEnglish = new Course(ENGLISH);

        studentRepository.save(studentAlice);
        studentRepository.save(studentBob);
        studentRepository.save(studentCindy);
        courseRepository.save(courseEnglish);
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

    @Test
    public void findClassMates_success() {
        studentAlice.signUp(courseEnglish);
        studentBob.signUp(courseEnglish);
        studentCindy.signUp(courseEnglish);

        var page1 = studentRepository.findClassMates(studentBob, courseEnglish, PageRequest.of(0, 1));
        var page2 = studentRepository.findClassMates(studentBob, courseEnglish, PageRequest.of(1, 1));

        assertThat(page1.getContent()).containsExactly(studentAlice);
        assertThat(page2.getContent()).containsExactly(studentCindy);
    }

    @Test
    public void findClassMates_not_signedUp() {
        studentAlice.signUp(courseEnglish);
        studentCindy.signUp(courseEnglish);

        var result = studentRepository.findClassMates(studentBob, courseEnglish, PageRequest.of(0, 1)).getContent();

        assertThat(result).isEmpty();
    }

    @Test
    public void findClassMates_not_found() {
        studentBob.signUp(courseEnglish);

        var result = studentRepository.findClassMates(studentBob, courseEnglish, PageRequest.of(0, 1)).getContent();

        assertThat(result).isEmpty();
    }

    @AfterEach
    public void tearDown() {
        studentRepository.delete(studentAlice);
        studentRepository.delete(studentBob);
        studentRepository.delete(studentCindy);
        courseRepository.delete(courseEnglish);
    }
}
