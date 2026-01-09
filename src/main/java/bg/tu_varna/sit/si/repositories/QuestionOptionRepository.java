package bg.tu_varna.sit.si.repositories;

import bg.tu_varna.sit.si.entities.QuestionOption;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class QuestionOptionRepository implements PanacheRepositoryBase<QuestionOption, UUID> {
}
