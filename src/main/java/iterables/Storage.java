package iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by vad0 on 15.07.17.
 */
public class Storage<T> {
    private final List<T> storage = new ArrayList<>();
    private final Supplier<T> factory;

    public Storage(Supplier<T> factory) {
        this.factory = factory;
    }

    public T get() {
        if (storage.isEmpty()) {
            return factory.get();
        } else {
            return storage.remove(storage.size() - 1);
        }
    }

    public void dispose(T element) {
        storage.add(element);
    }
}
