package bg.tu_varna.sit.si.dtos.responses;

import java.util.List;
import java.util.UUID;

public class QuestionResponse {

    public UUID id;
    public String text;
    public String type;
    public boolean required;
    public int orderIndex;
    public List<OptionResponse> options;
}