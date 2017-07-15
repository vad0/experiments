package kde;

/**
 * Created by vadim on 18.03.17.
 */
public class Array2d {

    private int columns;
    private long address;
    private long capacity;
    private int size = 0;

    public Array2d(int capacity, int columns) {
        this.columns = columns;
        this.capacity = capacity;
        this.address = Utils.UNSAFE.allocateMemory(Utils.DOUBLE_SIZE * columns * capacity);
    }

    public double get(int row, int column) {
        return Utils.UNSAFE.getDouble(address + Utils.DOUBLE_SIZE * (columns * row + column));
    }

    private void putUnchecked(int row, int column, double value) {
        long offset = Utils.DOUBLE_SIZE * (columns * row + column);
        assert row >= 0 && column >= 0 && offset >= 0;
/*
        System.out.println(String.format(
                "Size: %d, capacity: %d, offset: %d, Col: %d, row: %d, value: %f",
                size,
                capacity,
                offset,
                row,
                column,
                value));
*/
        Utils.UNSAFE.putDouble(address + offset, value);
//        System.out.println("Ok");
    }

    public void add1(double x1) {
        ensureCapacity();
        putUnchecked(size, 0, x1);
        size++;
    }

    public void add2(double x1, double x2) {
        ensureCapacity();
        putUnchecked(size, 0, x1);
        putUnchecked(size, 1, x2);
        size++;
    }

    public void add3(double x1, double x2, double x3) {
        ensureCapacity();
        putUnchecked(size, 0, x1);
        putUnchecked(size, 1, x2);
        putUnchecked(size, 2, x3);
        size++;
    }

    private void ensureCapacity() {
        if (size != capacity) {
            return;
        }
        // double up
//        System.out.println("Current capacity " + capacity);
        long currentBytes = Utils.DOUBLE_SIZE * columns * capacity;
//        System.out.println(String.format("Trying to allocate %d bytes", currentBytes * 2));
        long newAddress = Utils.UNSAFE.allocateMemory(currentBytes * 2);
//        System.out.println("Copying data to new location");
        Utils.UNSAFE.copyMemory(address, newAddress, currentBytes);
//        System.out.println("Freeing old memory location");
        Utils.UNSAFE.freeMemory(address);
//        System.out.println("Done");
        address = newAddress;
        capacity *= 2;
    }

    public int size() {
        return size;
    }

    public void reset() {
        size = 0;
    }
}
