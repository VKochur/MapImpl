package team.mediasoft.education.kvv.map;

import java.util.Set;

public interface PairsContainer<K, V> {

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    Set<K> keysSet();

    Set<Pair<K, V>> getPairs();

    boolean containsKey(K key);
}
