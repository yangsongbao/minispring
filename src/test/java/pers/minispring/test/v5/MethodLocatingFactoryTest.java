package pers.minispring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.aop.config.MethodLocatingFactory;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.core.io.Resource;
import pers.minispring.tx.TransactionManager;

import java.lang.reflect.Method;


public class MethodLocatingFactoryTest {
	@Test
	public void testGetMethod() throws Exception{
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		Resource resource = new ClassPathResource("petstore-v5.xml");
		reader.loadBeanDefinitions(resource);
		
		MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
		methodLocatingFactory.setTargetBeanName("tx");
		methodLocatingFactory.setMethodName("start");
		methodLocatingFactory.setBeanFactory(beanFactory);
		
		Method m = methodLocatingFactory.getObject();
		
		Assert.assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
		Assert.assertTrue(m.equals(TransactionManager.class.getMethod("start")));
		
	}
}
