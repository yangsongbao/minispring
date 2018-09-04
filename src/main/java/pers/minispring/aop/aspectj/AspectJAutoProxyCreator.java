package pers.minispring.aop.aspectj;


import pers.minispring.aop.Advice;
import pers.minispring.aop.MethodMatcher;
import pers.minispring.aop.Pointcut;
import pers.minispring.aop.framework.AopConfigSupport;
import pers.minispring.aop.framework.AopProxyFactory;
import pers.minispring.aop.framework.CglibProxyFactory;
import pers.minispring.aop.framework.JdkAopProxyFactory;
import pers.minispring.beans.BeansException;
import pers.minispring.beans.factory.config.BeanPostProcessor;
import pers.minispring.beans.factory.config.ConfigurableBeanFactory;
import pers.minispring.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AspectJAutoProxyCreator implements BeanPostProcessor {

	private ConfigurableBeanFactory beanFactory;

	@Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
		
		//如果这个Bean本身就是Advice及其子类，那就不要再生成动态代理了。
		if(isInfrastructureClass(bean.getClass())){
			return bean;
		}
		
		List<Advice> advices = getCandidateAdvices(bean);
		if(advices.isEmpty()){
			return bean;
		}
		
		return createProxy(advices,bean);
	}
	
	private List<Advice> getCandidateAdvices(Object bean){
		
		List<Object> advices = this.beanFactory.getBeansByType(Advice.class);
		
		List<Advice> result = new ArrayList<Advice>();
		for(Object o : advices){			
			Pointcut pc = ((Advice) o).getPointcut();
			if(canApply(pc,bean.getClass())){
				result.add((Advice) o);
			}
			
		}
		return result;
	}
	
	protected Object createProxy( List<Advice> advices ,Object bean) {
		
		
		AopConfigSupport config = new AopConfigSupport();
		for(Advice advice : advices){
			config.addAdvice(advice);
		}
		
		Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
		for (Class<?> targetInterface : targetInterfaces) {
			config.addInterface(targetInterface);
		}
		
		config.setTargetObject(bean);		
		
		AopProxyFactory proxyFactory;
		if(config.getProxiedInterfaces().length == 0){
			proxyFactory =  new CglibProxyFactory(config);
		} else{
			proxyFactory = new JdkAopProxyFactory(config);
		}	
	
		
		return proxyFactory.getProxy();
		
		
	}
	
	protected boolean isInfrastructureClass(Class<?> beanClass) {
		boolean retVal = Advice.class.isAssignableFrom(beanClass);
		
		return retVal;
	}
	
	public void setBeanFactory(ConfigurableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		
	}
	
	public static boolean canApply(Pointcut pc, Class<?> targetClass) {
		

		MethodMatcher methodMatcher = pc.getMethodMatcher();

		Set<Class> classes = new LinkedHashSet<Class>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
		classes.add(targetClass);
		for (Class<?> clazz : classes) {
			Method[] methods = clazz.getDeclaredMethods();			
			for (Method method : methods) {
				if (methodMatcher.matches(method/*, targetClass*/)) {
					return true;
				}
			}
		}

		return false;
	}

}
