package iterables;

import java.util.Iterator;

/**
 * Created by vad0 on 15.07.17.
 */
public class DisposableIterator<T>
        implements Iterator<T>, AutoCloseable {
    private static final Storage<DisposableIterator> STORAGE =
            new Storage<>(DisposableIterator::new);
    private DisposableIterable<T> iterable;
    private int index = 0;

    private DisposableIterator() {
    }

    static <T> DisposableIterator<T> create(DisposableIterable<T> iterable) {
        DisposableIterator<T> result = STORAGE.get();
        assert result.iterable == null && iterable != null;
        result.iterable = iterable;
        assert result.index == 0;
        return result;
    }

    @Override
    public boolean hasNext() {
        return index < iterable.getSize();
    }

    @Override
    public T next() {
        return iterable.get(index++);
    }

    @Override
    public void close() {
        index = 0;
        iterable.close();
        iterable = null;
    }
}
