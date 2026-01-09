package bg.tu_varna.sit.si.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class Question extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Survey survey;

    @Column(nullable = false, length = 2000)
    public String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public QuestionType type;

    @Column(nullable = false)
    public boolean required = false;

    @Column(nullable = false)
    public int orderIndex;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    public List<QuestionOption> options = new ArrayList<>();
}
