package kde;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by vad0 on 15.07.17.
 */
public class Utils {
    public static final Unsafe UNSAFE;
    public static final long DOUBLE_SIZE = 8;
    public static final long OBJECT_SIZE = 8;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
