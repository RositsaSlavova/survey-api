package bg.tu_varna.sit.si.repositories;

import bg.tu_varna.sit.si.entities.Submission;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SubmissionRepository implements PanacheRepositoryBase<Submission, UUID> {

    public List<Submission> findBySurveyId(UUID surveyId) {
        return list("survey.id = ?1", surveyId);
    }
}