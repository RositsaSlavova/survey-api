package bg.tu_varna.sit.si.dtos.responses;

import java.util.List;
import java.util.UUID;

public class SurveyResultsResponse {

    public UUID surveyId;
    public long totalSubmissions;
    public List<QuestionResultResponse> questions;
}