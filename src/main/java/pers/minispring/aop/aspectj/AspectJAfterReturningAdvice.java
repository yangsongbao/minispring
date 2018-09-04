package pers.minispring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import pers.minispring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author songbao.yang
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {

    public AspectJAfterReturningAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObject) {
        super(adviceMethod, pointcut, adviceObject);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        Object o = mi.proceed();
        //例如：调用TransactionManager的commit方法
        this.invokeAdviceMethod();
        return o;
    }

}