package ac.ir.urmia.Eight.DS.GraphToolkit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * HashTable Stores Key - value pairs.
 *
 * @param <K> The type of keys that is going to get stored.
 * @param <V> The type of values that is going to get stored.
 */

@SuppressWarnings("unchecked")
public class HashTable<K, V> {
    int size = 1;
    public node<K, V>[] table = new node[size];
    int occupied = 0;


    /**
     * method returns the string form of main table minus the chained nodes.
     *
     * @return The string form of main table minus the chained nodes.
     */
    @Override
    public String toString() {
        return Arrays.toString(table);

    }

    /**
     * Put a key - value pair in HasTable.
     *
     * @param key   the key.
     * @param value the value corresponding to the key.
     */

    public void put(K key, V value) {

        node<K, V> e = new node<>(key, value);
        int index = hashFIndex(key);
        if (table[index] != null) {
            node<K, V> current = table[index];
            while (current.next != null)
                current = current.next;
            current.next = e;
        } else
            table[index] = e;
        occupied++;
        assureCapacity();
    }

    /**
     * Finds the proper index for the key in table.
     *
     * @param key the key that is going to be maintained in HashTable.
     * @return the Index.
     */
    private int hashFIndex(K key) {
        return Math.abs(key.hashCode() % this.table.length);

    }

    /**
     * @param key the key of the key - value pair that is wanted to be removed.
     * @return the value of the key.
     */

    public V remove(K key) {
        int index = hashFIndex(key);
        node<K, V> current = table[index];
        node<K, V> previous;
        if (table[index] != null && table[index].next == null) {
            current = table[index];
            table[index] = null;
            return current.value;
        }

        while (current != null && !current.key.equals(key)) {
            if (current.next != null)
                if (current.next.key.equals(key)) {
                    previous = current;
                    previous.next = current.next.next;
                    return previous.getValue();
                }
            current = current.next;
        }

        if (current == null) return null;
        else return current.value;

    }

    /**
     * Returns the value corresponded to the given key.
     *
     * @param key the key.
     * @return the value of the key.
     */
    public V get(K key) {
        int index = hashFIndex(key);
        node<K, V> head = table[index];
        if (head == null) return null;
        while (!head.key.equals(key)) {
            if (head.next == null) return null;

            head = head.next;

        }
        return head.value;


    }

    /**
     * Checks if the given key is in the table or not.
     *
     * @param key the key that is wanted to be checked.
     * @return a boolean value.
     */
    public boolean containsKey(K key) {
        int index = hashFIndex(key);
        node<K, V> head = table[index];

        if (head == null) return false;
        if (head.key.equals(key)) return true;
        else
            while (!head.key.equals(key)) {
                if (head.next == null) return false;

                head = head.next;

            }

        return true;
    }


    /**
     * Assures the capacity and Rehashes the whole table.
     */

    private void assureCapacity() {
        if (0.75 * this.table.length <= this.occupied) {
            node<K, V>[] temp = this.table.clone();

            table = new node[2 * table.length];
            for (node<K, V> node : temp) {
                while (node != null) {
                    put(node.getKey(), node.getValue());
                    occupied--;
                    node = node.next;
                }
            }
        }
    }


    /**
     * @return Set of keys of the current table.
     */
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();


        for (node<K, V> node : this.table) {
            while (node != null) {
                keySet.add(node.key);
                node = node.next;
            }
        }


        return keySet;
    }

    /**
     * @param <K> Type of the keys.
     * @param <V> Type of the values.
     */
    protected static class node<K, V> {
        private K key;
        private V value;
        private node<K, V> next = null;

        public node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public node<K, V> getNext() {
            return next;
        }

        public void setNext(node<K, V> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }


}
