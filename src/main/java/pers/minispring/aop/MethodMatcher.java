package pers.minispring.aop;


import java.lang.reflect.Method;

/**
 * @author songbao.yang
 */
public interface MethodMatcher {

    boolean matches(Method method/*, Class<?> targetClass*/);

}