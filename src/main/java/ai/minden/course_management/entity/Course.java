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
@Table(name = "courses", schema = "course_management")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private final Set<Student> students = new HashSet<>();

    public Course(String name) {
        this.name = name;
    }
}
