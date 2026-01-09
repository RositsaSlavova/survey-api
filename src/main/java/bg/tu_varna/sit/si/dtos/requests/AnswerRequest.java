package bg.tu_varna.sit.si.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AnswerRequest {

    @NotNull
    public UUID questionId;

    @NotBlank
    public String value;
}