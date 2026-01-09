package bg.tu_varna.sit.si.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "answers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"submission_id", "question_id"})
        }
)
public class Answer extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Submission submission;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Question question;

    @Column(nullable = false, columnDefinition = "text")
    public String value;
}
