package bg.tu_varna.sit.si.repositories;

import bg.tu_varna.sit.si.entities.Question;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class QuestionRepository implements PanacheRepositoryBase<Question, UUID> {

    public List<Question> findBySurveyId(UUID surveyId) {
        return list("survey.id = ?1", surveyId);
    }

    public Integer findMaxOrderIndex(UUID surveyId) {
        Number result = (Number) getEntityManager()
                .createQuery(
                        "select max(q.orderIndex) from Question q where q.survey.id = :surveyId"
                )
                .setParameter("surveyId", surveyId)
                .getSingleResult();

        return result != null ? result.intValue() : null;
    }
}
