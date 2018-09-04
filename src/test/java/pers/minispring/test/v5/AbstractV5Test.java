package pers.minispring.test.v5;


import pers.minispring.aop.config.AspectInstanceFactory;
import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.core.io.Resource;
import pers.minispring.tx.TransactionManager;

import java.lang.reflect.Method;

public class AbstractV5Test {
		
	protected BeanFactory getBeanFactory(String configFile){
		DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
		Resource resource = new ClassPathResource(configFile);
		reader.loadBeanDefinitions(resource);	
		return  defaultBeanFactory;		
	}
	
	protected  Method getAdviceMethod( String methodName) throws Exception{
		return TransactionManager.class.getMethod(methodName);
	}
	
	protected AspectInstanceFactory getAspectInstanceFactory(String targetBeanName){
		AspectInstanceFactory factory = new AspectInstanceFactory();
		factory.setAspectBeanName(targetBeanName);		
		return factory;
	}

}
