package team.mediasoft.education.kvv.map.impl.support;

//hash code is different for not equals
public class GoodHash extends WrapperInt {

    public GoodHash() {
    }

    @Override
    public int hashCode() {
        return getValue();
    }
}
