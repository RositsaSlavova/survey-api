package bg.tu_varna.sit.si.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public class CreateSurveyRequest {

    @NotBlank
    public String title;

    public String description;
}
