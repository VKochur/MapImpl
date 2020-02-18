package team.mediasoft.education.kvv.map.impl;

import team.mediasoft.education.kvv.list.TwoDirectionList;
import team.mediasoft.education.kvv.map.PairsContainer;

public class TableReconstructorImpl<K, V> implements TableReconstructor<K,V> {

    private double koef;

    public TableReconstructorImpl(double koef) {
        if (koef <= 1) {
            throw new IllegalArgumentException("koef must be > 1");
        }
        this.koef = koef;
    }

    @Override
    public TwoDirectionList<PairsContainer.Pair<K, V>>[] reconstruct(TwoDirectionList<PairsContainer.Pair<K, V>>[] table) {
            int capacity = (int) (table.length * koef);
            TwoDirectionList<PairsContainer.Pair<K,V>>[] newTable = new TwoDirectionList[capacity];
            //init
            for (int i = 0; i < capacity; i++) {
                newTable[i] = new TwoDirectionList<>();
            }
            //filling
            for (TwoDirectionList<PairsContainer.Pair<K, V>> oldlRow : table) {
                int size = oldlRow.getSize();
                for (int i = 0; i < size; i++) {
                    PairsContainer.Pair<K, V> currentPair = oldlRow.getByIndex(i);
                    int newRowIndex = currentPair.getKey().hashCode() % capacity;
                    newTable[newRowIndex].addToLastPlace(currentPair);
                }
            }
            return newTable;
    }
}
