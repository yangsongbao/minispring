package pers.minispring.aop;


/**
 * @author songbao.yang
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();

    String getExpression();
}
