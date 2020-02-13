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
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("", 1);
        });
        list.add("1", 0);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("", 2);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("", -1);
        });
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
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.getByIndex(0);
        });
        for (int i = 0; i < 10; i++) {
            list.addToLastPlace(String.valueOf(i));
        }
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.getByIndex(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.getByIndex(10);
        });
        for (int i = 0; i < 10; i++) {
            assertEquals(String.valueOf(i), list.getByIndex(i));
        }
    }

    @Test
    void getFirstIndexOf() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        assertEquals(-1, list.getFirstIndexOf(null));
        assertEquals(-1, list.getFirstIndexOf(""));
        for (int i = 0; i < 10; i++) {
            list.addToLastPlace(String.valueOf(i));
        }
        assertEquals(-1, list.getFirstIndexOf(null));
        assertEquals(-1, list.getFirstIndexOf("10"));
        assertEquals(0, list.getFirstIndexOf("0"));
        assertEquals(6, list.getFirstIndexOf("6"));
        assertEquals(9, list.getFirstIndexOf("9"));

        list.addToLastPlace(null);
        assertEquals(10, list.getFirstIndexOf(null));

        for (int i = 0; i < 10; i++) {
            list.addToLastPlace(String.valueOf(i));
        }
        assertEquals(5, list.getFirstIndexOf("5"));
        list.addToFirstPlace(null);
        assertEquals(0, list.getFirstIndexOf(null));
    }

    @Test
    void getLastIndexOf() {
        TwoDirectionList list = new TwoDirectionList();
        assertEquals(-1, list.getLastIndexOf(null));
        assertEquals(-1, list.getLastIndexOf(""));
        for (int i = 0; i < 10; i++) {
            list.addToLastPlace(String.valueOf(i));
        }
        assertEquals(-1, list.getLastIndexOf(null));
        assertEquals(-1, list.getLastIndexOf("10"));
        assertEquals(0, list.getLastIndexOf("0"));
        assertEquals(6, list.getLastIndexOf("6"));
        assertEquals(9, list.getLastIndexOf("9"));

        list.addToLastPlace(null);
        assertEquals(10, list.getLastIndexOf(null));

        for (int i = 0; i < 10; i++) {
            list.addToLastPlace(String.valueOf(i));
        }

        assertEquals(17, list.getLastIndexOf("6"));
        list.addToLastPlace(null);
        assertEquals(21, list.getLastIndexOf(null));
    }

    @Test
    void contains() {
        TwoDirectionList<Object> list = new TwoDirectionList<>();
        assertFalse(list.contains("124"));
        assertFalse(list.contains(null));
        assertFalse(list.contains(TwoDirectionList.class));

        list.addToLastPlace("");
        list.addToLastPlace(new String("2"));
        list.addToFirstPlace(this.getClass());
        list.addToLastPlace(10);
        list.addToLastPlace(128);
        list.addToLastPlace(null);

        assertTrue(list.contains(""));
        assertTrue(list.contains("2"));
        assertTrue(list.contains(this.getClass()));
        assertTrue(list.contains(Integer.valueOf(10)));
        assertTrue(list.contains(Integer.valueOf(128)));
        assertTrue(list.contains(null));

        Object obj1 = new Object();
        assertFalse(list.contains(obj1));
        list.addToLastPlace(obj1);
        assertTrue(list.contains(obj1));
    }

    @Test
    void removeByIndex() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeByIndex(0);
        });
        for (int i = 0; i < 10; i++) {
            assertEquals(i, list.getSize());
            list.addToLastPlace(String.valueOf(i));
        }
        assertEquals(10, list.getSize());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeByIndex(10);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.removeByIndex(-1);
        });

        assertEquals("0", list.removeByIndex(0));
        assertEquals("1", list.removeByIndex(0));
        assertEquals("9", list.removeByIndex(7));
        assertEquals("8", list.removeByIndex(6));
        assertEquals("5", list.removeByIndex(3));

        assertEquals(5, list.getSize());

        String[] expected = {"2", "3", "4", "6", "7"};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.getByIndex(i));
        }

        for (int i = 0; i < 5; i++) {
            list.removeByIndex(0);
        }
        assertTrue(list.isEmpty());
    }

    @Test
    void remove() {
        TwoDirectionList<String> list = new TwoDirectionList<>();
        assertFalse(list.remove(""));
        list.addToLastPlace(null);
        assertFalse(list.remove(""));
        assertTrue(list.remove(null));
        for (int i = 0; i < 5; i++) {
            list.addToLastPlace(String.valueOf(i));
        }
        for (int i = 0; i < 5; i++) {
            list.addToLastPlace(String.valueOf(i));
        }

        String[] expected = new String[] {"0", "1", "2", "3", "4", "0", "1", "2", "3", "4"};
        checkByArray(expected, list);

        //remove inner
        assertTrue(list.remove("4"));
        expected = new String[] {"0", "1", "2", "3", "0", "1", "2", "3", "4"};
        checkByArray(expected, list);

        //remove inner
        assertTrue(list.remove("3"));
        expected = new String[] {"0", "1", "2", "0", "1", "2", "3", "4"};
        checkByArray(expected, list);

        //remove first
        assertTrue(list.remove("0"));
        expected = new String[] {"1", "2", "0", "1", "2", "3", "4"};
        checkByArray(expected, list);

        //remove last
        assertTrue(list.remove("4"));
        expected = new String[] {"1", "2", "0", "1", "2", "3"};
        checkByArray(expected, list);

        for (int i = 0; i < 4; i++) {
            while(list.remove(String.valueOf(i)));
        }
        assertEquals(0, list.getSize());
    }

    private void checkByArray(String[] expected, TwoDirectionList<String> list) {
        assertEquals(expected.length, list.getSize());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.getByIndex(i));
        }
    }
}
