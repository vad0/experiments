/**
 * Created by vadim on 18.03.17.
 */
public class SimpleKDE extends KDE {
    public SimpleKDE(Kernel kernel, int capacity) {
        super(kernel, capacity, 1);
    }

    public void addValue(double x) {
        array.add1(x);
    }

    @Override
    public double density(double x) {
        double numerator = 0;
        for (int row = 0; row < array.size(); row++) {
            numerator += kernel.calculate(x - x(row));
        }
        return numerator / array.size();
    }
}
