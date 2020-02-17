package team.mediasoft.education.kvv.map.impl;

import team.mediasoft.education.kvv.list.TwoDirectionList;


public interface TableTester<K, V> {

    /**
     * Decides if need reconstruct table or not
     *
     * By default only checks max basket's length
     *
     * @param basketForAdding where we want to add new pair
     * @param pairForAdding new pair
     * @param maxBasketLength expected value for basket's max length
     * @param table baskets
     * @return
     */
    default boolean needReconstructTable(TwoDirectionList<PairsContainerImpl.InnerPair<K,V>> basketForAdding,
                                 PairsContainerImpl.InnerPair<K,V> pairForAdding,
                                 int maxBasketLength,
                                 TwoDirectionList<PairsContainerImpl.InnerPair<K,V>>[] table) {

        return !(basketForAdding.getSize() < maxBasketLength);
    }
}
