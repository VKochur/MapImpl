package team.mediasoft.education.kvv.map.impl;

import org.junit.jupiter.api.Test;
import team.mediasoft.education.kvv.list.TwoDirectionList;

import static org.junit.jupiter.api.Assertions.*;

class TableTesterTest {

    @Test
    void testDefaultNeedReconstructTable() {
        TwoDirectionList<PairsContainerImpl.InnerPair<String, String>> basket = new TwoDirectionList<>();
        for (int i = 0; i < 5; i++) {
            basket.addToLastPlace(new PairsContainerImpl.InnerPair<>(String.valueOf(i), null));
        }
        TableTester<String, String> tableTester = new TableTester<String, String>() {};
        assertFalse(tableTester.needReconstructTable(basket, null, 6, null));
        assertTrue(tableTester.needReconstructTable(basket, null, 5, null));
        assertTrue(tableTester.needReconstructTable(basket, null, 4, null));
    }

}