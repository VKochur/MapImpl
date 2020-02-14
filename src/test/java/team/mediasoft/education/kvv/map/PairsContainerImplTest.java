package team.mediasoft.education.kvv.map;

import org.junit.jupiter.api.Test;

class PairsContainerImplTest {

    @Test
    void put() {
        PairsContainerImpl<String, String> pairContainer = new PairsContainerImpl<>();
        pairContainer.put("key1", "value1");
        pairContainer.put("key1", "value2");
    }
}