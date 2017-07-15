package iterables;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vad0 on 15.07.17.
 */
public class DisposableIterable<T>
        implements Iterable<T>, AutoCloseable {
    private static final Storage<DisposableIterable> STORAGE =
            new Storage<>(DisposableIterable::new);
    private final AtomicInteger refCounter = new AtomicInteger(0);
    private Object[] array;
    private int size = 0;

    public static <T> DisposableIterable<T> create() {
        DisposableIterable<T> result = STORAGE.get();
        int counter = result.refCounter.incrementAndGet();
        assert counter == 1;
        assert result.size == 0;
        if (result.array == null) {
            result.array = new Object[100];
        }
        return result;
    }

    private DisposableIterable() {
    }

    public int getSize() {
        return size;
    }

    public void add(T element) {
        if (refCounter.get() != 0) {
            throw new ConcurrentModificationException(
                    "Can't modify iterable after iteration has started");
        }
        if (size == array.length) {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size] = element;
        size++;
    }

    T get(int index) {
        assert index < size;
        return (T) array[index];
    }

    @Override
    public Iterator<T> iterator() {
        int counter = refCounter.incrementAndGet();
        assert counter > 0;
        return DisposableIterator.create(this);
    }

    @Override
    public void close() {
        int count = refCounter.decrementAndGet();
        if (count > 0) {
            return;
        }
        assert count == 0;
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        STORAGE.dispose(this);
    }
}
