package bg.tu_varna.sit.si.repositories;

import bg.tu_varna.sit.si.entities.Survey;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class SurveyRepository implements PanacheRepositoryBase<Survey, UUID> {
}
