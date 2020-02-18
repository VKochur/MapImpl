package team.mediasoft.education.kvv.map.impl;

import team.mediasoft.education.kvv.list.TwoDirectionList;
import team.mediasoft.education.kvv.map.PairsContainer;

import java.util.HashSet;
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

    private HashSet<K> keys;
    private Set<PairsContainer.Pair<K,V>> pairs;


    /*
      table :
      if (twoDirectionList<InnerPair<K,V>> = table[i]) =>
      for each innerPair :  innerPair.key.hashCode % currentCapacity = i
   */
    private TwoDirectionList<Pair<K, V>>[] table;

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

        table = createEmptyTable(capacity);
        keys = new HashSet<>();
        pairs = new HashSet<>();

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

    private TwoDirectionList<Pair<K, V>>[] createEmptyTable(int capacity) {
        TwoDirectionList<Pair<K, V>>[] table = new TwoDirectionList[capacity];
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
        Pair<K, V> newPair = new Pair<>(key, value);

        int rowNumber = defineRowNumber(newPair.getKey());
        TwoDirectionList<Pair<K, V>> basket = table[rowNumber];
        Pair<K, V> oldPair = addedWithCheckForExist(basket, newPair);
        if (oldPair == null) {
            return null;
        } else {
            return oldPair.getValue();
        }
    }

    private Pair<K, V> addedWithCheckForExist(TwoDirectionList<Pair<K, V>> basket, Pair<K, V> newPair) {
        int indexOfExisted = basket.getFirstIndexOf(newPair);
        if (indexOfExisted == -1) {
            //not existed
            addNewPairInBasket(basket, newPair);
            return null;
        } else {
            //existed already
            Pair<K, V> oldPair = basket.set(newPair, indexOfExisted);
            pairs.remove(oldPair);
            pairs.add(newPair);
            return oldPair;
        }
    }

    private void addNewPairInBasket(TwoDirectionList<Pair<K, V>> basket, Pair<K, V> newPair) {
        if (needReconstructTable(basket, newPair)) {
            reconstructTable();
            int rowNumber = defineRowNumber(newPair.getKey());
            //change basket
            basket = table[rowNumber];
        }
        basket.addToLastPlace(newPair);
        keys.add(newPair.getKey());
        pairs.add(newPair);
    }

    private void reconstructTable() {
        TwoDirectionList[] reconstruct = tableReconstructor.reconstruct(unmodifiableTable(table));
        table = reconstruct;
        currentCapacity = reconstruct.length;
    }

    private boolean needReconstructTable(TwoDirectionList<Pair<K, V>> basket, Pair<K, V> newPair) {
        return tableTester.needReconstructTable(basket, newPair, maxBasketLength, unmodifiableTable(table));
    }

    //todo implementation
    private TwoDirectionList<Pair<K, V>>[] unmodifiableTable(TwoDirectionList<Pair<K, V>>[] table) {

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
        Pair<K, V> forSearch = new Pair<>(key, null);
        TwoDirectionList<Pair<K, V>> basket = table[basketIndex];
        int firstIndexOf = basket.getFirstIndexOf(forSearch);
        if (firstIndexOf == -1) {
            return null;
        } else {
            return basket.getByIndex(firstIndexOf).getValue();
        }
    }

    @Override
    public V remove(K key) {
        int basketIndex = defineRowNumber(key);
        Pair<K, V> forSearch = new Pair<>(key, null);
        TwoDirectionList<Pair<K, V>> basket = table[basketIndex];
        int firstIndexOf = basket.getFirstIndexOf(forSearch);
        if (firstIndexOf == -1) {
            return null;
        } else {
            Pair<K, V> deleted = basket.removeByIndex(firstIndexOf);
            keys.remove(deleted.getKey());
            pairs.remove(forSearch);
            return deleted.getValue();
        }
    }

    @Override
    public Set<K> keysSet() {
        return new HashSet<>(keys);
    }

    @Override
    public Set<Pair<K, V>> getPairs() {
        return new HashSet<>(pairs);
    }

    @Override
    public boolean containsKey(K key) {
        int basketIndex = defineRowNumber(key);
        Pair<K, V> forSearch = new Pair<>(key, null);
        TwoDirectionList<Pair<K, V>> basket = table[basketIndex];
        return (basket.getFirstIndexOf(forSearch) != -1);
    }
}
