/**
 * Created by vadim on 18.03.17.
 */
public class WeightedKDE extends KDE {
    public WeightedKDE(Kernel kernel, int capacity) {
        super(kernel, capacity, 2);
    }

    public void addValue(double x, double weight) {
        assert weight > 0;
        array.add2(x, weight);
    }

    @Override
    public double density(double x) {
        double numerator = 0, denominator = 0;
        for (int row = 0; row < array.size(); row++) {
            double weight = weight(row);
            numerator += weight * kernel.calculate(x - x(row));
            denominator += weight;
        }
        return numerator / denominator;
    }

    private double weight(int row) {
        return array.get(row, 1);
    }
}
