package bg.tu_varna.sit.si.repositories;

import bg.tu_varna.sit.si.entities.Answer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AnswerRepository implements PanacheRepositoryBase<Answer, UUID> {

    public List<Answer> findBySubmissionId(UUID submissionId) {
        return list("submission.id = ?1", submissionId);
    }

    public List<Answer> findByQuestionId(UUID questionId) {
        return list("question.id = ?1", questionId);
    }
}