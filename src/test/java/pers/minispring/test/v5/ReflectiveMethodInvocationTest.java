package pers.minispring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pers.minispring.aop.aspectj.AspectJAfterReturningAdvice;
import pers.minispring.aop.aspectj.AspectJAfterThrowingAdvice;
import pers.minispring.aop.aspectj.AspectJBeforeAdvice;
import pers.minispring.aop.aspectj.AspectJExpressionPointcut;
import pers.minispring.aop.config.AspectInstanceFactory;
import pers.minispring.aop.framework.ReflectiveMethodInvocation;
import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.service.v5.PetStoreService;
import pers.minispring.tx.TransactionManager;
import pers.minispring.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveMethodInvocationTest extends AbstractV5Test {
	
	
	private AspectJBeforeAdvice beforeAdvice = null;
	private AspectJAfterReturningAdvice afterAdvice = null;
	private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
	private PetStoreService petStoreService = null;
	private TransactionManager tx;

    private AspectJExpressionPointcut pc = null;
    private BeanFactory beanFactory = null;
    private AspectInstanceFactory aspectInstanceFactory = null;


    @Before
	public  void setUp() throws Exception{
        petStoreService = new PetStoreService();
        tx = new TransactionManager();

        MessageTracker.clearMsgs();

        beanFactory = this.getBeanFactory("petstore-v5.xml");
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        beforeAdvice = new AspectJBeforeAdvice(
                this.getAdviceMethod("start"),
                null,
                aspectInstanceFactory);

        afterAdvice = new AspectJAfterReturningAdvice(
                this.getAdviceMethod("commit"),
                null,
                aspectInstanceFactory);

        afterThrowingAdvice = new AspectJAfterThrowingAdvice(
                this.getAdviceMethod("rollback"),
                null,
                aspectInstanceFactory
        );
		
	}

	
	@Test
	public void testMethodInvocation() throws Throwable{
		
		
		Method targetMethod = PetStoreService.class.getMethod("placeOrder");
		
		List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
		interceptors.add(beforeAdvice);
		interceptors.add(afterAdvice);	
		
		
		ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService,targetMethod,new Object[0],interceptors);
		
		mi.proceed();
		
		
		List<String> msgs = MessageTracker.getMsgs();
		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));	
		Assert.assertEquals("place order", msgs.get(1));	
		Assert.assertEquals("commit tx", msgs.get(2));	
		
	}
	
	@Test
	public void testMethodInvocation2() throws Throwable{
		
		
		Method targetMethod = PetStoreService.class.getMethod("placeOrder");
		
		List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
		interceptors.add(afterAdvice);	
		interceptors.add(beforeAdvice);
		
		
		
		ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService,targetMethod,new Object[0],interceptors);
		
		mi.proceed();
		
		
		List<String> msgs = MessageTracker.getMsgs();
		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));	
		Assert.assertEquals("place order", msgs.get(1));	
		Assert.assertEquals("commit tx", msgs.get(2));	
		
	}
	@Test
	public void testAfterThrowing() throws Throwable{
		
		
		Method targetMethod = PetStoreService.class.getMethod("placeOrderWithException");
		
		List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
		interceptors.add(afterThrowingAdvice);	
		interceptors.add(beforeAdvice);
		
		
		
		ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService,targetMethod,new Object[0],interceptors);
		try{
			mi.proceed();	
			
		}catch(Throwable t){
			List<String> msgs = MessageTracker.getMsgs();
			Assert.assertEquals(2, msgs.size());
			Assert.assertEquals("start tx", msgs.get(0));			
			Assert.assertEquals("rollback tx", msgs.get(1));	
			return;
		}
			
		
		Assert.fail("No Exception thrown");	
		
		
	}
	
}
