package team.mediasoft.education.kvv.map;

import java.util.Objects;
import java.util.Set;

public interface PairsContainer<K, V> {

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    Set<K> keysSet();

    Set<Pair<K, V>> getPairs();

    boolean containsKey(K key);


    /**
     * equals, hashCode by only key
     *
     * @param <K> key's class
     * @param <V> value's class
     */
    class Pair<K, V> {

        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(key, pair.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public String toString() {
            return "PairsContainer.Pair{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
