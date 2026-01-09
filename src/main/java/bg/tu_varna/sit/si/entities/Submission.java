package bg.tu_varna.sit.si.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "submissions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"accessCode_id"})
        }
)
public class Submission extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Survey survey;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    public AccessCode accessCode;

    @Column(nullable = false)
    public Instant submittedAt = Instant.now();

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Answer> answers = new ArrayList<>();
}
