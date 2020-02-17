package team.mediasoft.education.kvv.map.impl.support;

//hash code is constant
public class BadHash extends WrapperInt {

    public static final int HASH_CODE_VALUE = 1;

    public BadHash() {
    }

    @Override
    public int hashCode() {
        return HASH_CODE_VALUE;
    }
}
