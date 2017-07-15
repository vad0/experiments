package kde;

import weka.estimators.UnivariateKernelEstimator;

import java.time.Duration;
import java.util.Random;

/**
 * Created by vadim on 18.03.17.
 */
public class Main {

    public static final Random RANDOM = new Random();
    public static final double MAX = 10;
    public static final double MIN = 0;
    public static final double MID = (MAX + MIN) / 2;
    public static final double RANGE = (MAX - MIN) / 2;
    public static final int N = 1000_000;
    public static final int STEPS = 50;

    public static void main(String[] args) {
        benchmarkSimpleKde();
        benchmarkWeightedKde();
        benchmarkWekaKde();
    }

    private static void benchmarkSimpleKde() {
        System.out.println("Simple");
        long start = System.nanoTime();
        SimpleKDE simpleKDE = getSimpleKDE();
        long endFill = System.nanoTime();
        fillGridKde(simpleKDE);
        long end = System.nanoTime();
        System.out.println("Fill time: " + Duration.ofNanos(endFill - start));
        System.out.println("Calculation time: " + Duration.ofNanos(end - endFill));
    }

    private static void benchmarkWeightedKde() {
        System.out.println("Weighted");
        long start = System.nanoTime();
        WeightedKDE simpleKDE = getWeightedKDE();
        long endFill = System.nanoTime();
        fillGridKde(simpleKDE);
        long end = System.nanoTime();
        System.out.println("Fill time: " + Duration.ofNanos(endFill - start));
        System.out.println("Calculation time: " + Duration.ofNanos(end - endFill));
    }

    private static void benchmarkWekaKde() {
        System.out.println("Weka");
        long start = System.nanoTime();
        UnivariateKernelEstimator simpleKDE = getWekaKDE();
        long endFill = System.nanoTime();
        fillGridWeka(simpleKDE);
        long end = System.nanoTime();
        System.out.println("Fill time: " + Duration.ofNanos(endFill - start));
        System.out.println("Calculation time: " + Duration.ofNanos(end - endFill));
    }

    private static void fillGridKde(KDE kde) {
        double[][] grid = new double[STEPS][2];
        for (int i = 0; i < STEPS; i++) {
            double x = MIN + RANGE * i / (STEPS - 1);
            grid[i][0] = x;
            grid[i][1] = kde.density(x);
        }
        System.out.println(grid[10][1]);
    }

    private static void fillGridWeka(UnivariateKernelEstimator wekaKDE) {
        double[][] wekaGrid = new double[STEPS][2];
        for (int i = 0; i < STEPS; i++) {
            double x = MIN + RANGE * i / (STEPS - 1);
            wekaGrid[i][0] = x;
            wekaGrid[i][1] = Math.exp(wekaKDE.logDensity(x));
        }
        System.out.println(wekaGrid[10][1]);

    }

    private static SimpleKDE getSimpleKDE() {
        SimpleKDE simpleKDE = new SimpleKDE(Kernel.gaussian(1), N / 1024);
        for (int i = 0; i < N; i++) {
            double x = RANGE * (RANDOM.nextGaussian() + MID);
            simpleKDE.addValue(x);
        }
        return simpleKDE;
    }

    private static WeightedKDE getWeightedKDE() {
        WeightedKDE weightedKDE = new WeightedKDE(Kernel.gaussian(1), N / 1024);
        for (int i = 0; i < N; i++) {
            double x = RANGE * (RANDOM.nextGaussian() + MID);
            weightedKDE.addValue(x, 1);
        }
        return weightedKDE;
    }

    private static UnivariateKernelEstimator getWekaKDE() {
        UnivariateKernelEstimator wekaKDE = new UnivariateKernelEstimator();
        for (int i = 0; i < N; i++) {
            double x = RANGE * (RANDOM.nextGaussian() + MID);
            wekaKDE.addValue(x, 1);
        }
        return wekaKDE;
    }
}
