package bg.tu_varna.sit.si.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class GenerateCodesRequest {

    @Min(1)
    @Max(1000)
    public int count;
}
