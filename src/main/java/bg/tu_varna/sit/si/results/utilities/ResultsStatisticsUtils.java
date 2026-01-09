package bg.tu_varna.sit.si.results.utilities;

import bg.tu_varna.sit.si.dtos.NumericStats;
import bg.tu_varna.sit.si.entities.Answer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public final class ResultsStatisticsUtils {

    private ResultsStatisticsUtils() {}

    public static LinkedHashMap<String, Long> singleValueCounts(List<Answer> answers) {
        Map<String, Long> temporary = new HashMap<>();
        for (Answer answer : answers) {
            if (answer.value == null) continue;
            String key = answer.value.trim();
            if (key.isEmpty()) continue;
            temporary.put(key, temporary.getOrDefault(key, 0L) + 1);
        }
        return sortByCountDescThenKey(temporary);
    }

    public static LinkedHashMap<String, Long> multiChoiceCountsCsv(List<Answer> answers) {
        Map<String, Long> temporary = new HashMap<>();
        for (Answer answer : answers) {
            if (answer.value == null) continue;
            String[] parts = answer.value.split(",");
            for (String part : parts) {
                String key = part.trim();
                if (key.isEmpty()) continue;
                temporary.put(key, temporary.getOrDefault(key, 0L) + 1);
            }
        }
        return sortByCountDescThenKey(temporary);
    }

    public static LinkedHashMap<String, Double> toPercentMap(Map<String, Long> counts, long answeredCount) {
        LinkedHashMap<String, Double> out = new LinkedHashMap<>();
        if (answeredCount <= 0) return out;

        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            double pct = (entry.getValue() * 100.0) / answeredCount;
            out.put(entry.getKey(), round2(pct));
        }
        return out;
    }

    public static NumericStats numericStatsFromAnswers(List<Answer> answers) {
        List<Double> nums = answers.stream()
                .map(a -> safeParseDouble(a.value))
                .filter(Objects::nonNull)
                .toList();

        if (nums.isEmpty()) return NumericStats.empty();

        double min = nums.stream().min(Double::compareTo).orElse(0.0);
        double max = nums.stream().max(Double::compareTo).orElse(0.0);
        double avg = nums.stream().mapToDouble(d -> d).average().orElse(0.0);

        return new NumericStats(min, max, avg);
    }

    private static LinkedHashMap<String, Long> sortByCountDescThenKey(Map<String, Long> input) {
        return input.entrySet().stream()
                .sorted((a, b) -> {
                    int compare = Long.compare(b.getValue(), a.getValue());
                    if (compare != 0) return compare;
                    return a.getKey().compareToIgnoreCase(b.getKey());
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (x, y) -> x,
                        LinkedHashMap::new
                ));
    }

    private static double round2(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private static Double safeParseDouble(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        try {
            return Double.parseDouble(t);
        } catch (Exception e) {
            return null;
        }
    }
}
