package team.mediasoft.education.kvv.map.impl;

import team.mediasoft.education.kvv.list.TwoDirectionList;
import team.mediasoft.education.kvv.map.PairsContainer;

public interface TableReconstructor<K, V> {

    TwoDirectionList<PairsContainer.Pair<K, V>>[] reconstruct(TwoDirectionList<PairsContainer.Pair<K, V>>[] table);
}
