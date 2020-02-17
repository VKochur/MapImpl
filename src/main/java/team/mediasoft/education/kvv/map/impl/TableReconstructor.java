package team.mediasoft.education.kvv.map.impl;

import team.mediasoft.education.kvv.list.TwoDirectionList;

public interface TableReconstructor<K, V> {

    TwoDirectionList<PairsContainerImpl.InnerPair<K, V>>[] reconstruct(TwoDirectionList<PairsContainerImpl.InnerPair<K, V>>[] table);
}
