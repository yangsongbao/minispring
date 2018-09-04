package pers.minispring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import pers.minispring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author songbao.yang
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {

    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObject) {

        super(adviceMethod, pointcut, adviceObject);
    }


    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable t) {
            invokeAdviceMethod();
            throw t;
        }
    }

}
