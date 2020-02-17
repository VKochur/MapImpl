package team.mediasoft.education.kvv.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import team.mediasoft.education.kvv.map.impl.PairsContainerImpl;
import team.mediasoft.education.kvv.map.impl.support.BadHash;
import team.mediasoft.education.kvv.map.impl.support.GoodHash;
import team.mediasoft.education.kvv.map.impl.support.WrapperInt;

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
}