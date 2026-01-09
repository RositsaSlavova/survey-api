package bg.tu_varna.sit.si.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "question_options")
public class QuestionOption extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Question question;

    @Column(nullable = false)
    public String label;

    @Column(nullable = false)
    public int orderIndex;
}
