package kde;

/**
 * Created by vadim on 18.03.17.
 */
public abstract class Kernel {
    private final double bandwidth;

    protected Kernel(double bandwidth) {
        assert bandwidth > 0;
        this.bandwidth = bandwidth;
    }

    public static Kernel epanechnikov(double bandwidth) {
        return new EpanechnikovKernel(bandwidth);
    }

    public static Kernel gaussian(double bandwidth) {
        return new GaussianKernel(bandwidth);
    }

    public double calculate(double x) {
        return calculateSimple(x / bandwidth) / bandwidth;
    }

    protected abstract double calculateSimple(double x);

    private static class EpanechnikovKernel extends Kernel {
        protected EpanechnikovKernel(double bandwidth) {
            super(bandwidth);
        }

        protected double calculateSimple(double x) {
            double xsq = x * x;
            return xsq > 1 ? 0 : 3 / 4 * (1 - xsq);
        }
    }

    private static class GaussianKernel extends Kernel {
        protected GaussianKernel(double bandwidth) {
            super(bandwidth);
        }

        protected double calculateSimple(double x) {
            return Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI);
        }
    }
}
