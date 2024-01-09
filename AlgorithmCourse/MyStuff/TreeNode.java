import oy.interact.tira.util.Visitable;
import oy.interact.tira.util.Visitor;

public class TreeNode<K extends Comparable<K>, V> implements Visitable<K, V> {
    K key;
    V value;
    TreeNode<K, V> left;
    TreeNode<K, V> right;

    TreeNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    @Override
    public void accept(Visitor<K, V> visitor) throws Exception {
        visitor.visit(this);
    }

    public K getKey() {
        return key;
    }

    public TreeNode<K, V> getLeft() {
        return left;
    }

    public TreeNode<K, V> getRight() {
        return right;
    }
}
