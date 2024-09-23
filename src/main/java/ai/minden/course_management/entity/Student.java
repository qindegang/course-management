package ai.minden.course_management.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(of = {"email"})
@ToString(of = {"email"})
@Getter
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "course_signup", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private final Set<Course> courses = new HashSet<>();

    public Student(String email) {
        this.email = email;
    }

    public void signUp(final Course course) {
        this.courses.add(course);
    }

    public void cancelSignUp(final Course course) {
        this.courses.remove(course);
    }
}
