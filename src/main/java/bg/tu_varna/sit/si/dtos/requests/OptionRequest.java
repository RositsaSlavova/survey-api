package bg.tu_varna.sit.si.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public class OptionRequest {

    @NotBlank
    public String label;

    public Integer orderIndex;
}
