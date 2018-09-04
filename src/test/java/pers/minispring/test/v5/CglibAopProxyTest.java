package pers.minispring.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pers.minispring.aop.aspectj.AspectJAfterReturningAdvice;
import pers.minispring.aop.aspectj.AspectJBeforeAdvice;
import pers.minispring.aop.aspectj.AspectJExpressionPointcut;
import pers.minispring.aop.config.AspectInstanceFactory;
import pers.minispring.aop.framework.AopConfig;
import pers.minispring.aop.framework.AopConfigSupport;
import pers.minispring.aop.framework.CglibProxyFactory;
import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.service.v5.PetStoreService;
import pers.minispring.tx.TransactionManager;
import pers.minispring.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.List;


public class CglibAopProxyTest extends AbstractV5Test {

    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterAdvice = null;
    private static AspectJExpressionPointcut pc = null;
    private BeanFactory beanFactory = null;
    private  AspectInstanceFactory aspectInstanceFactory = null;

    private TransactionManager tx;

    @Before
    public void setUp() throws Exception {

        tx = new TransactionManager();

        String expression = "execution(* pers.minispring.service.v5.*.placeOrder(..))";

        pc = new AspectJExpressionPointcut();

        pc.setExpression(expression);

        beanFactory = this.getBeanFactory("petstore-v5.xml");
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        beforeAdvice = new AspectJBeforeAdvice(
                getAdviceMethod("start"),
                pc,
                aspectInstanceFactory);

        afterAdvice = new AspectJAfterReturningAdvice(
                getAdviceMethod("commit"),
                pc,
                aspectInstanceFactory);
    }

    @Test
    public void testGetProxy() {

        AopConfig config = new AopConfigSupport();

        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(new PetStoreService());

        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        PetStoreService proxy = (PetStoreService) proxyFactory.getProxy();

        proxy.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

        proxy.toString();
    }

}
