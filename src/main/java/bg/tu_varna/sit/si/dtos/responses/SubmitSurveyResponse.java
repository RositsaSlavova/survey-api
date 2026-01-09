package bg.tu_varna.sit.si.dtos.responses;


import java.time.Instant;
import java.util.UUID;

public class SubmitSurveyResponse {

    public UUID submissionId;
    public Instant submittedAt;

    public SubmitSurveyResponse(UUID submissionId, Instant submittedAt) {
        this.submissionId = submissionId;
        this.submittedAt = submittedAt;
    }
}
