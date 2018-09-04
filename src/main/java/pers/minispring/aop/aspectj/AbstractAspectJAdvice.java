package pers.minispring.aop.aspectj;


import pers.minispring.aop.Advice;
import pers.minispring.aop.Pointcut;
import pers.minispring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author songbao.yang
 */
public abstract class AbstractAspectJAdvice implements Advice {


    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected AspectInstanceFactory adviceObjectFactory;

    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 AspectInstanceFactory adviceObjectFactory) {

        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObjectFactory = adviceObjectFactory;
    }


    public void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceInstance() throws Exception {
        return adviceObjectFactory.getAspectInstance();
    }
}