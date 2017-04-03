package com.ladislav;

/**
 *  Simple class that corresponds to class Entry<K, V>;
 *  User can set any data type, but it is recommended for key to be immutable.
 */
public class KVPair<K, V> {
    private final K key;
    private V value;

    public KVPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj instanceof KVPair) {
            KVPair other = (KVPair) obj;
            if (this.key.equals(other.key) && this.value.equals(other.value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }


    public static void main(String[] args) {
        StringBuilder s = new StringBuilder("Ladislav");
        KVPair<StringBuilder, Integer> kvp = new KVPair<>(s, 1);

        s.append(" Milunovic");

        System.out.println(kvp.getKey());

    }
}

