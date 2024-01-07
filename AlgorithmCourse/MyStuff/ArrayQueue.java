import.QueueInterface;

public class ArrayQueue<E> implements QueueInterface<E> {
    private E[] array;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayQueue() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayQueue(int capacity) {
        array = (E[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return array.length;
    }

    @Override
    public void enqueue(E element) throws OutOfMemoryError, NullPointerException {
        if (element == null)
            throw new NullPointerException();
        try {
            if (size == array.length) {
                reallocate();
            }
            array[tail] = element;
            tail = (tail + 1) % array.length;
            size++;
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError("Failed to reallocate the queue");
        }
    }

    @Override
    public E dequeue() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        E element = array[head];
        array[head] = null;
        head = (head + 1) % array.length;
        size--;
        return element;
    }

    @Override
    public E element() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return array[head];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        head = 0;
        tail = 0;
        size = 0;
    }

    private void reallocate() {
        E[] newArray = (E[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head + i) % array.length];
        }
        array = newArray;
        head = 0;
        tail = size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[(head + i) % array.length]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
