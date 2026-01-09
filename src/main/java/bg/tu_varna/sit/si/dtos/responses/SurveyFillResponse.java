package bg.tu_varna.sit.si.dtos.responses;

import java.util.List;
import java.util.UUID;

public class SurveyFillResponse {

    public UUID id;
    public String title;
    public String description;
    public List<QuestionResponse> questions;
}