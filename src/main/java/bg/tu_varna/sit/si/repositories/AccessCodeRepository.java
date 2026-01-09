package bg.tu_varna.sit.si.repositories;

import bg.tu_varna.sit.si.entities.AccessCode;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccessCodeRepository implements PanacheRepositoryBase<AccessCode, UUID> {

    public boolean existsByCode(String code) {
        return count("code", code) > 0;
    }

    public Optional<AccessCode> findUnusedBySurveyIdAndCode(UUID surveyId, String code) {
        return find(
                "survey.id = ?1 and code = ?2 and used = false",
                surveyId,
                code
        ).firstResultOptional();
    }

    public List<AccessCode> findUnusedBySurveyId(UUID surveyId) {
        return list("survey.id = ?1 and used = false", surveyId);
    }
}