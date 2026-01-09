package bg.tu_varna.sit.si.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "access_codes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code"})
        }
)
public class AccessCode extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Survey survey;

    @Column(nullable = false, length = 32)
    public String code;

    @Column(nullable = false)
    public boolean used = false;

    public Instant usedAt;
}
