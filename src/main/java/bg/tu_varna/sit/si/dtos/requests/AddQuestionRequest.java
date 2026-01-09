package bg.tu_varna.sit.si.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AddQuestionRequest {

    @NotBlank
    public String text;

    @NotNull
    public String type;

    public boolean required = false;

    public Integer orderIndex;

    public List<OptionRequest> options;
}