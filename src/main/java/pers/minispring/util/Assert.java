package pers.minispring.util;

/**
 * @author songbao.yang
 */
public abstract class Assert {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
