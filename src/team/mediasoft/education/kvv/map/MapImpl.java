package team.mediasoft.education.kvv.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapImpl<K, V> implements Map<K, V> {

    private final int DEFAULT_INITIAL_CAPACITY = 10;

    //count rows in table
    private int currentCapacity;
    //count pair
    private int size;

    private Pair<K, V>[] table;

    public MapImpl() {
        this.currentCapacity = DEFAULT_INITIAL_CAPACITY;
    }

    public MapImpl(int currentCapacity) {
        if (currentCapacity < 1) {
            throw new IllegalArgumentException("capacity should be > 0");
        }
        this.currentCapacity = currentCapacity;
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
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
