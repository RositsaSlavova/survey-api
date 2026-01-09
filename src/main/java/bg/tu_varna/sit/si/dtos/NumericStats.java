package bg.tu_varna.sit.si.dtos;

public class NumericStats {

    public final Double min;
    public final Double max;
    public final Double avg;

    public NumericStats(Double min, Double max, Double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public static NumericStats empty() {
        return new NumericStats(null, null, null);
    }
}