public class StackImplementation<E> implements StackInterface<E>{
    private static final int DEFAULT_STACK_SIZE = 10;
    private Object[] itemArray;
    private int lastItem;  

    public StackImplementation() {
        this(DEFAULT_STACK_SIZE);
    }

    public StackImplementation(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        itemArray = new Object[capacity];
        lastItem = -1; 
    }

    @Override
    public int capacity() {
        return itemArray.length;
    }

    @Override
    public void push(E element) throws OutOfMemoryError, NullPointerException {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
    
        if (lastItem == itemArray.length - 1) {
            int newCapacity = itemArray.length * 2;
            if (newCapacity < 0) {
                throw new OutOfMemoryError("Stack capacity exceeded");
            }
            
            Object[] newArray = new Object[newCapacity];
    
            for (int i = 0; i <= lastItem; i++) {
                newArray[i] = itemArray[i];
            }
    
            itemArray = newArray;
        }
        itemArray[++lastItem] = element;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        E element = peek();
        itemArray[lastItem--] = null; 
        return element;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return (E) itemArray[lastItem];
    }

    @Override
    public boolean isEmpty() {
        return lastItem == -1;
    }

    @Override
    public int size() {
        return lastItem + 1;
    }

    @Override
    public void clear() {
        for (int i = 0; i <= lastItem; i++) {
            itemArray[i] = null;
        }
        lastItem = -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        
        for (int i = 0; i <= lastItem; i++) {
            builder.append(itemArray[i]);
            if (i < lastItem) {
                builder.append(", ");
            }
        }
        
        builder.append("]");
        
        return builder.toString();
    }
    
}
