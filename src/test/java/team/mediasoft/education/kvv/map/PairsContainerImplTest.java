package team.mediasoft.education.kvv.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import team.mediasoft.education.kvv.map.impl.PairsContainerImpl;
import team.mediasoft.education.kvv.map.impl.support.BadHash;
import team.mediasoft.education.kvv.map.impl.support.GoodHash;
import team.mediasoft.education.kvv.map.impl.support.WrapperInt;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

class PairsContainerImplTest {

    @Test
    void constructor() {
        assertThrows(IllegalArgumentException.class, () -> new PairsContainerImpl<>(0, 2));
        assertThrows(IllegalArgumentException.class, () -> new PairsContainerImpl<>(2, 0));
    }

    @Test
    void put() {
        PairsContainerImpl<WrapperInt, String> pairContainer = new PairsContainerImpl<>(3, 10);
        for (int i = 0; i < 30; i++) {
            WrapperInt key = new GoodHash();
            key.setValue(i);
            assertNull(pairContainer.put(key, String.valueOf(i)));
        }
        GoodHash key = new GoodHash();
        key.setValue(10);
        assertEquals("10", pairContainer.get(key));
        assertEquals("10", pairContainer.put(key, "new_10"));
        assertEquals("new_10", pairContainer.get(key));
    }

    @Test
    void get() {
        PairsContainerImpl<WrapperInt, String> pairsContainer = new PairsContainerImpl<>();
        assertNull(pairsContainer.get(null));
        assertNull(pairsContainer.get(new BadHash()));
        pairsContainer = generateData();
        for (int i = 0; i < 30; i++) {
            WrapperInt key;

            key = new GoodHash();
            key.setValue(i);
            assertEquals(getValueForGood(i), pairsContainer.get(key));

            key = new BadHash();
            key.setValue(i);
            assertEquals(getValueForBad(i), pairsContainer.get(key));
        }

        assertEquals(getValueForNull(), pairsContainer.get(null));
        assertEquals(null, pairsContainer.get(getKeyForNull()));

        //and check by not existed key
        assertNull(pairsContainer.get(new WrapperInt()));
    }

    private PairsContainerImpl<WrapperInt, String> generateData() {
        PairsContainerImpl<WrapperInt, String> pairsContainer = new PairsContainerImpl<>(5, 5);
        //add with good key's hash
        for (int i = 0; i < 30; i++) {
            WrapperInt key = new GoodHash();
            key.setValue(i);
            pairsContainer.put(key, getValueForGood(i));
        }
        //add with bad key's hash
        for (int i = 0; i < 30; i++) {
            WrapperInt key = new BadHash();
            key.setValue(i);
            pairsContainer.put(key, getValueForBad(i));
        }

        pairsContainer.put(null, getValueForNull());
        pairsContainer.put(getKeyForNull(), null);

        return pairsContainer;
    }

    private WrapperInt getKeyForNull() {
        GoodHash key = new GoodHash();
        key.setValue(-100);
        return key ;
    }

    private String getValueForNull() {
        return "value for key = null";
    }


    private String getValueForBad(int i) {
        return "bad_" + i;
    }

    private String getValueForGood(int i) {
        return "good_" + i;
    }

    @Test
    void remove() {
        PairsContainerImpl<WrapperInt, String> pairsContainer = generateData();
        GoodHash key = new GoodHash();
        key.setValue(-101);
        assertNull(pairsContainer.remove(key));
        assertNull(pairsContainer.remove(getKeyForNull()));

        key = new GoodHash();
        key.setValue(1);
        assertEquals(getValueForGood(1), pairsContainer.get(key));
        assertEquals(getValueForGood(1), pairsContainer.remove(key));
        assertNull(pairsContainer.get(key));
    }

    @Test
    void containsKey() {
        PairsContainerImpl pairsContainer = new PairsContainerImpl();
        Object key = new Object();
        assertFalse(pairsContainer.containsKey(key));
        assertEquals(null, pairsContainer.put(key, ""));
        assertTrue(pairsContainer.containsKey(key));
        assertEquals("", pairsContainer.remove(key));
        assertFalse(pairsContainer.containsKey(key));
    }

    @Test
    void keysSet() {
        PairsContainerImpl pairsContainer = new PairsContainerImpl();
        assertTrue(pairsContainer.keysSet().isEmpty());
        Object key = new Object();
        pairsContainer.put(key, "1");
        assertEquals(1, pairsContainer.keysSet().size());
        assertTrue(pairsContainer.keysSet().contains(key));
        pairsContainer.put(key, "2");
        assertEquals(1, pairsContainer.keysSet().size());
        assertTrue(pairsContainer.keysSet().contains(key));
        HashSet<String> expectedKeys = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String currentKey = String.valueOf(i);
            expectedKeys.add(currentKey);
            pairsContainer.put(currentKey, "");
        }
        assertEquals(101, pairsContainer.keysSet().size());
        assertTrue(pairsContainer.keysSet().containsAll(expectedKeys));
        assertFalse(expectedKeys.containsAll(pairsContainer.keysSet()));
        pairsContainer.remove(key);
        assertTrue(expectedKeys.containsAll(pairsContainer.keysSet()));

        for (int i = 0; i < 10; i++) {
            pairsContainer.put(String.valueOf(i), "other value");
            assertEquals(100, pairsContainer.keysSet().size());
        }

        for (int i = 0; i < 100; i++) {
            pairsContainer.remove(String.valueOf(i));
            assertFalse(pairsContainer.keysSet().contains(String.valueOf(i)));
            assertEquals(99 - i, pairsContainer.keysSet().size());
        }

        assertTrue(pairsContainer.keysSet().isEmpty());
    }

    @Test
    void getPairs() {
        PairsContainer<Integer, String> pairsContainer = new PairsContainerImpl();
        assertTrue(pairsContainer.getPairs().isEmpty());
        int countPairs = 30;
        Set<PairsContainer.Pair> expectedPairs = new HashSet<>();
        for (int i = 0; i < countPairs; i++) {
            expectedPairs.add(new PairsContainer.Pair(i, null));
            pairsContainer.put(i, String.valueOf(i));
        }

        Set<PairsContainer.Pair<Integer, String>> pairs = pairsContainer.getPairs();
        for (PairsContainer.Pair<Integer, String> pair : pairs) {
            Integer key = pair.getKey();
            assertEquals(String.valueOf(key), pair.getValue());
        }

        int key = 10;
        pairsContainer.put(key, "newValue");
        PairsContainer.Pair<Integer, String> pairByKey = getPairByKey(pairsContainer, key);
        assertEquals("newValue", pairByKey.getValue());

        for (int i = 0; i < 30; i++) {
            pairsContainer.remove(i);
            assertEquals(29 - i, pairsContainer.getPairs().size());
        }
        assertTrue(pairsContainer.getPairs().isEmpty());
    }

    private PairsContainer.Pair<Integer, String> getPairByKey(PairsContainer<Integer, String> pairsContainer, int key) {
        Stream<PairsContainer.Pair<Integer, String>> stream = pairsContainer.getPairs().stream();
        return stream.filter(integerStringPair -> integerStringPair.getKey().equals(key)).findFirst().get();
    }
}