package bg.tu_varna.sit.si.dtos.responses;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class SubmissionDetailsResponse {
    public UUID id;
    public UUID surveyId;
    public UUID accessCodeId;
    public Instant submittedAt;
    public List<AnswerDetailsResponse> answers;
}