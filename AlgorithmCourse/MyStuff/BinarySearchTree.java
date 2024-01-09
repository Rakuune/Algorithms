import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import oy.interact.tira.util.Pair;
import oy.interact.tira.util.TIRAKeyedOrderedContainer;
import oy.interact.tira.util.Visitor;

public class BinarySearchTreeContainer<K extends Comparable<K>, V> implements TIRAKeyedOrderedContainer<K, V> {
    TreeNode<K, V> root;
    int size;
    private Comparator<K> comparator;

    public BinarySearchTreeContainer(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and Value must not be null");
        }
        K existingKey = findKeyByValue(root, value);
        if (existingKey != null) {
            remove(existingKey); 
        }
        root = insertRecursively(root, key, value);
        size++;
    }

    private TreeNode<K, V> insertRecursively(TreeNode<K, V> current, K key, V value) {
        if (current == null) {
            return new TreeNode<>(key, value);
        }
        int cmp = compare(key, current.key);
        if (cmp < 0) {
            current.left = insertRecursively(current.left, key, value);
        } else {
            current.right = insertRecursively(current.right, key, value);
        }
        return current;
    }

    private K findKeyByValue(TreeNode<K, V> current, V value) {
        if (current == null) {
            return null;
        }
        if (value.equals(current.value)) {
            return current.key;
        }
        K leftResult = findKeyByValue(current.left, value);
        if (leftResult != null) {
            return leftResult;
        }
        return findKeyByValue(current.right, value);
    }

    private int compare(K key1, K key2) {
        if (comparator != null) {
            return comparator.compare(key1, key2);
        }
        return key1.compareTo(key2);
    }

    private boolean keyExists(TreeNode<K, V> current, K key) {
        if (current == null) {
            return false;
        }
        int cmp = compare(key, current.key);
        if (cmp < 0) {
            return keyExists(current.left, key);
        } else if (cmp > 0) {
            return keyExists(current.right, key);
        } else {
            return true; 
        }
    }

    @Override
    public V get(K key) {
        return getRecursively(root, key);
    }

    private V getRecursively(TreeNode<K, V> current, K key) {
        if (current == null) {
            return null;
        }
        if (key.equals(current.key)) {
            return current.value;
        }
        return key.compareTo(current.key) < 0 ? getRecursively(current.left, key) : getRecursively(current.right, key);
    }

    private TreeNode<K, V> findNode(K key) {
        return findNodeRecursively(root, key);
    }

    private TreeNode<K, V> findNodeRecursively(TreeNode<K, V> current, K key) {
        if (current == null) {
            return null;
        }
        if (key.equals(current.key)) {
            return current;
        }
        return key.compareTo(current.key) < 0 ? findNodeRecursively(current.left, key)
                : findNodeRecursively(current.right, key);
    }

    @Override
    public V remove(K key) {
        if (keyExists(root, key)) {
            TreeNode<K, V> toRemove = findNode(key);
            if (toRemove != null) {
                root = removeRecursively(root, key);
                size--;
                return toRemove.value;
            }
        }
        return null;
    }

    private TreeNode<K, V> removeRecursively(TreeNode<K, V> current, K key) {
        if (current == null) {
            return null;
        }
        if (key.equals(current.key)) {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            TreeNode<K, V> smallestValue = findSmallestValue(current.right);
            current.key = smallestValue.key;
            current.value = smallestValue.value;
            current.right = removeRecursively(current.right, smallestValue.key);
            return current;
        }
        if (key.compareTo(current.key) < 0) {
            current.left = removeRecursively(current.left, key);
            return current;
        }
        current.right = removeRecursively(current.right, key);
        return current;
    }

    private TreeNode<K, V> findSmallestValue(TreeNode<K, V> root) {
        return root.left == null ? root : findSmallestValue(root.left);
    }

    @Override
    public V find(Predicate<V> searcher) {
        return findInOrder(root, searcher);
    }

    private V findInOrder(TreeNode<K, V> current, Predicate<V> searcher) {
        if (current == null) {
            return null;
        }
        V leftResult = findInOrder(current.left, searcher);
        if (leftResult != null) {
            return leftResult;
        }
        if (searcher.test(current.value)) {
            return current.value;
        }
        return findInOrder(current.right, searcher);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void ensureCapacity(int capacity) {
        throw new UnsupportedOperationException("Capacity not supported in BST");
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public Pair<K, V>[] toArray() {
        List<Pair<K, V>> list = new ArrayList<>();
        toArrayRecursively(root, list);
        return list.toArray(new Pair[0]);
    }

    private void toArrayRecursively(TreeNode<K, V> node, List<Pair<K, V>> list) {
        if (node != null) {
            toArrayRecursively(node.left, list);
            list.add(new Pair<>(node.key, node.value));
            toArrayRecursively(node.right, list);
        }
    }

    @Override
    public int indexOf(K itemKey) {
        return indexOfRecursively(root, itemKey, 0);
    }

    private int indexOfRecursively(TreeNode<K, V> node, K key, int currentIndex) {
        if (node == null) {
            return -1; 
        }

        int cmp = compare(key, node.key);
        if (cmp < 0) {
            return indexOfRecursively(node.left, key, currentIndex);
        } else if (cmp > 0) {
            int rightTreeIndex = currentIndex + 1 + (node.left != null ? countNodes(node.left) : 0);
            return indexOfRecursively(node.right, key, rightTreeIndex);
        } else {
            return currentIndex + (node.left != null ? countNodes(node.left) : 0);
        }
    }

    class Index {
        int value = -1;
    }

    @Override
    public Pair<K, V> getIndex(int index) throws IndexOutOfBoundsException {
        int[] count = new int[1]; 
        Pair<K, V> result = getIndexRecursively(root, index, count);
        if (result == null) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return result;
    }

    private Pair<K, V> getIndexRecursively(TreeNode<K, V> node, int index, int[] count) {
        if (node == null) {
            return null;
        }

        
        Pair<K, V> leftResult = getIndexRecursively(node.left, index, count);
        if (leftResult != null) {
            return leftResult;
        }

        
        if (count[0] == index) {
            return new Pair<>(node.key, node.value);
        }
        count[0]++; 

        
        return getIndexRecursively(node.right, index, count);
    }

    @Override
    public int findIndex(Predicate<V> searcher) {
        return findIndexRecursively(root, searcher, 0).orElse(-1); // Return -1 if not found
    }

    private Optional<Integer> findIndexRecursively(TreeNode<K, V> node, Predicate<V> searcher, int currentIndex) {
        if (node == null) {
            return Optional.empty();
        }

        Optional<Integer> leftResult = findIndexRecursively(node.left, searcher, currentIndex);
        if (leftResult.isPresent()) {
            return leftResult;
        }

        
        int currentIdx = currentIndex + (node.left != null ? countNodes(node.left) : 0);

        if (searcher.test(node.value)) {
            return Optional.of(currentIdx);
        }

        
        return findIndexRecursively(node.right, searcher, currentIdx + 1);
    }

    private int countNodes(TreeNode<K, V> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    @Override
    public void accept(Visitor<K, V> visitor) throws Exception {
        acceptRecursively(root, visitor);
    }

    private void acceptRecursively(TreeNode<K, V> node, Visitor<K, V> visitor) throws Exception {
        if (node != null) {
            visitor.visit(node);
            acceptRecursively(node.left, visitor);
            acceptRecursively(node.right, visitor);
        }
    }

}
