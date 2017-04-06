package com.ladislav;

/**
 *  Simple class that corresponds to standard Java class EntryPair<K, V>
 *  User can set any data type as key and value, but it is recommended for key to be immutable.
 *  Class has simple constructor, getters and setters.
 *  Overridden methods: equals(), hashCode() and toString()
 */


public class EntryPair<K, V> {
    private final K key;
    private V value;

    public EntryPair(K key, V value) {
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

        if (obj instanceof EntryPair) {
            EntryPair other = (EntryPair) obj;
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
}

