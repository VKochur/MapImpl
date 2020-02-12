package team.mediasoft.education.kvv.list;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class TwoDirectionListTest {

    @Test
    void getFirstFromEmpty() {
        TwoDirectionList<String> emptyList = new TwoDirectionList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> emptyList.getFirst());
    }

    @Test
    void getFirst() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        list.addToFirstPlace("first");
        assertEquals("first", list.getFirst());
        list.addToFirstPlace("second");
        assertEquals("second", list.getFirst());
    }

    @Test
    void getLastFromEmpty() {
        TwoDirectionList<String> emptyList = new TwoDirectionList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> emptyList.getLast());
    }

    @Test
    void getLast() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        list.addToLastPlace("first");
        assertEquals("first", list.getLast());
        list.addToLastPlace("second");
        assertEquals("second", list.getLast());
    }

    @Test
    void isEmpty() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        assertTrue(list.isEmpty());
        list.add("first", 0);
        assertFalse(list.isEmpty());
    }

    @Test
    void addToLastPlace() {
        TwoDirectionList<Integer> list = new TwoDirectionList<>();
        Integer first = Integer.valueOf(0);
        list.addToLastPlace(first);
        for (int i = 1; i < 4; i++) {
            Integer expected = Integer.valueOf(i);
            list.addToLastPlace(expected);
            assertEquals(first, list.getFirst());
            assertEquals(expected, list.getLast());
        }
    }

    @Test
    void addToFirstPlace() {
        TwoDirectionList<Integer> list = new TwoDirectionList<>();
        Integer last = Integer.valueOf(0);
        list.addToFirstPlace(last);
        for (int i = 0; i < 4; i++) {
            Integer expected = Integer.valueOf(i);
            list.addToFirstPlace(expected);
            assertEquals(expected, list.getFirst());
            assertEquals(last, list.getLast());
        }
    }

    @Test
    void addIntoAndOutBorders() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add("", 1);});
        list.add("1", 0);
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add("", 2);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add("", -1);});
        list.add("2", 1);
        list.add("3", 2);
        list.add("0", 0);
        assertEquals("0", list.getFirst());
        assertEquals("3", list.getLast());
    }

    @Test
    void addIntoInner() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        for (int i = 0; i < 10; i++) {
            list.addToLastPlace(String.valueOf(i));
        }
        //to first part of list
        list.add("first", 4);
        assertEquals("3", list.getByIndex(3));
        assertEquals("first", list.getByIndex(4));
        assertEquals("4", list.getByIndex(5));

        //to centre of list
        list.add("centre", 5);
        assertEquals("first", list.getByIndex(4));
        assertEquals("centre", list.getByIndex(5));
        assertEquals("4", list.getByIndex(6));

        //to second part of list
        list.add("second", 6);
        assertEquals("centre", list.getByIndex(5));
        assertEquals("second", list.getByIndex(6));
        assertEquals("4", list.getByIndex(7));
    }

    @Test
    void getByIndex() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> {list.getByIndex(0);});
        for (int i = 0; i < 10; i++) {
            list.addToLastPlace(String.valueOf(i));
        }
        assertThrows(IndexOutOfBoundsException.class, ()->{list.getByIndex(-1);});
        assertThrows(IndexOutOfBoundsException.class, ()->{list.getByIndex(10);});
        for (int i = 0; i < 10; i++) {
            assertEquals(String.valueOf(i), list.getByIndex(i));
        }
    }
}
