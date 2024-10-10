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

    @OneToMany(mappedBy = "student")
    private final Set<SignUp> signUps = new HashSet<>();

    public Student(String email) {
        this.email = email;
    }
}
