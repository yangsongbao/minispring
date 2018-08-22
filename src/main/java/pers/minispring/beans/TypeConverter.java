package pers.minispring.beans;

/**
 * @author songbao.yang
 */
public interface TypeConverter {

    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;

}
