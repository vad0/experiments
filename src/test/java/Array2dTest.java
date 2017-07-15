import kde.Array2d;
import org.junit.Test;

/**
 * Created by vadim on 19.03.17.
 */
public class Array2dTest {
    @Test
    public void testAdd2() {
        Array2d array2d = new Array2d(3, 2);
        int N = 10;
        double[][] check = new double[N][2];
        for (int i = 0; i < N; i++) {
            double x = Math.random();
            double y = Math.random();
            array2d.add2(x, y);
            check[i][0] = x;
            check[i][1] = y;
        }
        assert array2d.size() == N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 2; j++) {
                assert check[i][j] == array2d.get(i, j);
            }
        }
    }

    @Test
    public void add2() throws Exception {

    }

    @Test
    public void add3() throws Exception {

    }

    @Test
    public void size() throws Exception {

    }

    @Test
    public void reset() throws Exception {

    }
}