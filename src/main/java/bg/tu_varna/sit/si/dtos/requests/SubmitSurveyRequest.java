package bg.tu_varna.sit.si.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class SubmitSurveyRequest {

    @NotBlank
    public String code;

    @NotEmpty
    public List<AnswerRequest> answers;
}