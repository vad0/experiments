public abstract class KDE {
    protected final Kernel kernel;
    protected final Array2d array;

    public KDE(Kernel kernel, int capacity, int columns) {
        this.kernel = kernel;
        this.array = new Array2d(capacity, columns);
    }

    public abstract double density(double x);

    protected double x(int row) {
        return array.get(row, 0);
    }

    public void reset() {
        array.reset();
    }
}
