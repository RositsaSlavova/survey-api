package bg.tu_varna.sit.si.dtos.responses;

import java.time.Instant;
import java.util.UUID;

public class SurveyResponse {

    public UUID id;
    public String title;
    public String description;
    public boolean active;
    public Instant createdAt;
}

