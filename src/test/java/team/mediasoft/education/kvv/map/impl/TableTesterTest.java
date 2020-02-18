package team.mediasoft.education.kvv.map.impl;

import org.junit.jupiter.api.Test;
import team.mediasoft.education.kvv.list.TwoDirectionList;
import team.mediasoft.education.kvv.map.PairsContainer;

import static org.junit.jupiter.api.Assertions.*;

class TableTesterTest {

    @Test
    void testDefaultNeedReconstructTable() {
        TwoDirectionList<PairsContainer.Pair<String, String>> basket = new TwoDirectionList<>();
        for (int i = 0; i < 5; i++) {
            basket.addToLastPlace(new PairsContainer.Pair<>(String.valueOf(i), null));
        }
        TableTester<String, String> tableTester = new TableTester<String, String>() {};
        assertFalse(tableTester.needReconstructTable(basket, null, 6, null));
        assertTrue(tableTester.needReconstructTable(basket, null, 5, null));
        assertTrue(tableTester.needReconstructTable(basket, null, 4, null));
    }

}