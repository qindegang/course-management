package ai.minden.course_management.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString(of = {"name"})
@Getter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "course")
    private final Set<SignUp> signUps = new HashSet<>();

    public Course(String name) {
        this.name = name;
    }
}
