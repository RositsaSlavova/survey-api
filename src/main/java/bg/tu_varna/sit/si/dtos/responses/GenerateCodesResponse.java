package bg.tu_varna.sit.si.dtos.responses;

import java.util.List;

public class GenerateCodesResponse {

    public int count;
    public List<String> codes;

    public GenerateCodesResponse(int count, List<String> codes) {
        this.count = count;
        this.codes = codes;
    }
}
