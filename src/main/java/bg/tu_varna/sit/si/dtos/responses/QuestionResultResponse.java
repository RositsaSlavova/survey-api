package bg.tu_varna.sit.si.dtos.responses;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuestionResultResponse {

    public UUID questionId;
    public String text;
    public String type;
    public int orderIndex;

    public long answeredCount;

    // TEXT
    public List<String> samples;

    // NUMBER/RATING
    public Double avg;
    public Double min;
    public Double max;

    // SINGLE_CHOICE / MULTIPLE_CHOICE / RATING
    public Map<String, Long> distribution;

    // Проценти за distribution (0..100)
    public Map<String, Double> distributionPercent;
}
