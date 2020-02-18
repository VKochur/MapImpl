package team.mediasoft.education.kvv.map.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import team.mediasoft.education.kvv.list.TwoDirectionList;
import team.mediasoft.education.kvv.map.PairsContainer;
import team.mediasoft.education.kvv.map.impl.support.BadHash;
import team.mediasoft.education.kvv.map.impl.support.GoodHash;
import team.mediasoft.education.kvv.map.impl.support.WrapperInt;

import static org.junit.jupiter.api.Assertions.*;

class TableReconstructorImplTest {

    //length of table's row
    private static int countPairForAdding;

    //count of rows
    private static int capacity;

    @BeforeAll
    static void init() {
        capacity = 3;
        countPairForAdding = 7;
    }

    @Test
    void testDefaultReconstruct() throws InstantiationException, IllegalAccessException {

        assertThrows(NullPointerException.class, () -> new TableReconstructorImpl(2).reconstruct(null));

        checkReconstructWithBadHash();
        checkReconstructWithGoodHash();
    }

    private void checkReconstructWithGoodHash() throws InstantiationException, IllegalAccessException {
        TwoDirectionList<PairsContainer.Pair<? extends GoodHash, String>>[] originalTable = table(GoodHash.class);
        checkContentWithGoodHash(originalTable);
        final double koef = 1.5;
        TwoDirectionList[] reconstructTable = new TableReconstructorImpl(koef).reconstruct(originalTable);
        int expectedCapacity = (int) (originalTable.length * koef);
        assertEquals(expectedCapacity, reconstructTable.length);
        checkContentWithGoodHashAfterReconstruct(reconstructTable);
    }

    private void checkContentWithGoodHashAfterReconstruct(TwoDirectionList[] reconstructTable) {
        //logic is the same as checkContent before reconstruct
        checkContentWithGoodHash(reconstructTable);
    }

    private void checkContentWithGoodHash(TwoDirectionList<PairsContainer.Pair<? extends GoodHash, String>>[] table) {
        for (int i = 0; i < countPairForAdding; i++) {
            int expectedIndexRow = i % table.length;
            TwoDirectionList<PairsContainer.Pair<? extends GoodHash, String>> expectedList = table[expectedIndexRow];
            GoodHash expectedKey = new GoodHash();
            expectedKey.setValue(i);
            assertTrue(expectedList.contains(new PairsContainer.Pair<>(expectedKey, null)));
        }
    }

    private void checkReconstructWithBadHash() throws InstantiationException, IllegalAccessException {
        TwoDirectionList<PairsContainer.Pair<? extends WrapperInt, String>>[] allPairsInOneRow = table(BadHash.class);
        checkContentWithBadHash(allPairsInOneRow);
        assertThrows(IllegalArgumentException.class, () ->  new TableReconstructorImpl(0.9));
        final double koef = 2.;

        TwoDirectionList[] reconstructTable = new TableReconstructorImpl(koef).reconstruct(allPairsInOneRow);

        int expectedCapacity = (int) (allPairsInOneRow.length * koef);
        assertEquals(expectedCapacity, reconstructTable.length);
        checkContentWithBadHashAfterReconstruct(reconstructTable);
    }

    private void checkContentWithBadHashAfterReconstruct(TwoDirectionList[] reconstructTable) {
        //must match with before construct
        checkContentWithBadHash(reconstructTable);
    }

    private void checkContentWithBadHash(TwoDirectionList<PairsContainer.Pair<? extends WrapperInt, String>>[] allPairsInOneRow) {
        int expectedRowIndex = BadHash.HASH_CODE_VALUE % capacity;
        TwoDirectionList<PairsContainer.Pair<? extends WrapperInt, String>> aloneFullRow = allPairsInOneRow[expectedRowIndex];
        for (int i = 0; i < countPairForAdding; i++) {
            PairsContainer.Pair<? extends WrapperInt, String> pair = aloneFullRow.getByIndex(i);
            assertTrue(i == pair.getKey().getValue());
        }
    }

    private <E extends WrapperInt> TwoDirectionList<PairsContainer.Pair<? extends E, String>>[] table(Class<? extends E> clazz) throws IllegalAccessException, InstantiationException {
        TwoDirectionList<PairsContainer.Pair<? extends E, String>>[] table = new TwoDirectionList[capacity];

        //init
        for (int i = 0; i < capacity; i++) {
            table[i] = new TwoDirectionList<>();
        }

        for (int i = 0; i < countPairForAdding; i++) {
            E instance = clazz.newInstance();
            instance.setValue(i);
            PairsContainer.Pair<E, String> pair = new PairsContainer.Pair<>(instance, String.valueOf(i));
            int rowIndex = pair.getKey().hashCode() % capacity;
            table[rowIndex].addToLastPlace(pair);
        }
        return table;
    }


}