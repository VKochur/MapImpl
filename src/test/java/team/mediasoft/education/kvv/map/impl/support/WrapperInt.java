package team.mediasoft.education.kvv.map.impl.support;

public class WrapperInt {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperInt wrapperInt1 = (WrapperInt) o;
        return getValue() == wrapperInt1.value;
    }

    @Override
    public String toString() {
        return "WrapperInt{" +
                "value=" + value +
                '}';
    }
}
