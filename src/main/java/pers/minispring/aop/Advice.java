package pers.minispring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author songbao.yang
 */
public interface Advice extends MethodInterceptor {
    public Pointcut getPointcut();
}

