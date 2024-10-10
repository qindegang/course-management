package ai.minden.course_management.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "course_signups")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class SignUp {

    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Id implements Serializable {
        @Column(name = "student_id")
        private Long studentId;

        @Column(name = "course_id")
        private Long courseId;

        public Id(Long studentId, Long courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }
    }

    @EmbeddedId
    private final Id id = new Id();

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    public SignUp(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.id.courseId = course.getId();
        this.id.studentId = student.getId();
        student.getSignUps().add(this);
        course.getSignUps().add(this);
    }
}
