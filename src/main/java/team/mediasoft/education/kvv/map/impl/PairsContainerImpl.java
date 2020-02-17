package team.mediasoft.education.kvv.map.impl;

import team.mediasoft.education.kvv.list.TwoDirectionList;
import team.mediasoft.education.kvv.map.Pair;
import team.mediasoft.education.kvv.map.PairsContainer;

import java.util.Objects;
import java.util.Set;

public class PairsContainerImpl<K, V> implements PairsContainer<K, V> {

    private static final int MIN_CAPACITY = 1;
    private static final int BASKET_MIN_LENGTH = 1;

    private final int DEFAULT_INITIAL_CAPACITY = 10;
    private final int DEFAULT_BASKET_MAX_LENGTH = 10;

    private final double RECONSTRUCT_KOEF = 1.5;

    //count rows in table
    private int currentCapacity;

    //max length of basket
    private int maxBasketLength;

    //count pairs
    private int size;

    /*
      table :
      if (twoDirectionList<InnerPair<K,V>> = table[i]) =>
      for each innerPair :  innerPair.key.hashCode % currentCapacity = i
   */
    private TwoDirectionList<InnerPair<K, V>>[] table;

    //tester table for need reconstruction
    private TableTester<K, V> tableTester;

    //reconstructor table
    private TableReconstructor tableReconstructor;


    public PairsContainerImpl() {
        init(DEFAULT_INITIAL_CAPACITY, DEFAULT_BASKET_MAX_LENGTH);
    }

    public PairsContainerImpl(int capacity, int basketMaxLength) {
        if (capacity < MIN_CAPACITY) {
            throw new IllegalArgumentException("capacity must be >= " + MIN_CAPACITY);
        }
        if (basketMaxLength < BASKET_MIN_LENGTH) {
            throw new IllegalArgumentException("basket's length must be >= " + BASKET_MIN_LENGTH);
        }
        init(capacity, basketMaxLength);
    }

    private void init(int capacity, int basketMaxLength) {
        currentCapacity = capacity;
        maxBasketLength = basketMaxLength;

        size = 0;
        table = createEmptyTable(capacity);

        tableTester = tableTesterByDefault();
        tableReconstructor = tableReconstructorByDefault();
    }

    private TableTester<K, V> tableTesterByDefault() {
        return new TableTester<K, V>() {
        };
    }

    public TableTester<K, V> getTableTester() {
        return tableTester;
    }

    public void setTableTester(TableTester<K, V> tableTester) {
        if (tableTester == null) {
            throw new NullPointerException("tableTester can't be null");
        } else {
            this.tableTester = tableTester;
        }
    }

    private TableReconstructor tableReconstructorByDefault() {
        return new TableReconstructorImpl(RECONSTRUCT_KOEF);
    }

    public TableReconstructor getTableReconstructor() {
        return tableReconstructor;
    }

    public void setTableReconstructor(TableReconstructor tableReconstructor) {
        if (tableReconstructor == null) {
            throw new NullPointerException("tableReconstruction can't be null");
        } else {
            this.tableReconstructor = tableReconstructor;
        }
    }

    private TwoDirectionList<InnerPair<K, V>>[] createEmptyTable(int capacity) {
        TwoDirectionList<InnerPair<K, V>>[] table = new TwoDirectionList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new TwoDirectionList<>();
        }
        return table;
    }

    /**
     * @param key
     * @param value
     * @return old value, if it existed by specific key. If it didn't exist, return null
     * But can return null, if null is a value with specific key.
     */
    @Override
    public V put(K key, V value) {
        InnerPair<K, V> newPair = new InnerPair<>(key, value);

        int rowNumber = defineRowNumber(newPair.getKey());
        TwoDirectionList<InnerPair<K, V>> basket = table[rowNumber];
        InnerPair<K, V> oldPair = addedWithCheckForExist(basket, newPair);
        if (oldPair == null) {
            return null;
        } else {
            return oldPair.getValue();
        }
    }

    private InnerPair<K, V> addedWithCheckForExist(TwoDirectionList<InnerPair<K, V>> basket, InnerPair<K, V> newPair) {
        int indexOfExisted = basket.getFirstIndexOf(newPair);
        if (indexOfExisted == -1) {
            //not existed
            addNewPairInBasket(basket, newPair);
            return null;
        } else {
            //existed already
            InnerPair<K, V> oldPair = basket.set(newPair, indexOfExisted);
            return oldPair;
        }
    }

    private void addNewPairInBasket(TwoDirectionList<InnerPair<K, V>> basket, InnerPair<K, V> newPair) {
        if (needReconstructTable(basket, newPair)) {
            reconstructTable();
            int rowNumber = defineRowNumber(newPair.getKey());
            //change basket
            basket = table[rowNumber];
        }
        basket.addToLastPlace(newPair);
        size++;
    }

    private void reconstructTable() {
        TwoDirectionList[] reconstruct = tableReconstructor.reconstruct(unmodifiableTable(table));
        table = reconstruct;
        currentCapacity = reconstruct.length;
    }

    private boolean needReconstructTable(TwoDirectionList<InnerPair<K, V>> basket, InnerPair<K, V> newPair) {
        return tableTester.needReconstructTable(basket, newPair, maxBasketLength, unmodifiableTable(table));
    }

    //todo implementation
    private TwoDirectionList<InnerPair<K, V>>[] unmodifiableTable(TwoDirectionList<InnerPair<K, V>>[] table) {

        return table;
    }

    private int defineRowNumber(K key) {
        if (key == null) {
            return 0;
        } else {
            return Math.abs(key.hashCode() % currentCapacity);
        }
    }

    @Override
    public V get(K key) {
        int basketIndex = defineRowNumber(key);
        InnerPair<K, V> forSearch = new InnerPair<>(key, null);
        TwoDirectionList<InnerPair<K, V>> basket = table[basketIndex];
        int firstIndexOf = basket.getFirstIndexOf(forSearch);
        if (firstIndexOf == -1) {
            return null;
        } else {
            return basket.getByIndex(firstIndexOf).getValue();
        }
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Pair<K, V> getPairs() {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    /**
     * overrided equals, hashCode by only key
     *
     * @param <K> key's class
     * @param <V> value's class
     */
    public static class InnerPair<K, V> {

        private K key;
        private V value;

        public InnerPair(K key, V value) {
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
            InnerPair<?, ?> innerPair = (InnerPair<?, ?>) o;
            return Objects.equals(key, innerPair.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public String toString() {
            return "InnerPair{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
