package pers.minispring.test.v6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pers.minispring.context.ApplicationContext;
import pers.minispring.context.support.ClassPathXmlApplicationContext;
import pers.minispring.service.v6.IPetStoreService;
import pers.minispring.util.MessageTracker;

import java.util.List;

public class ApplicationContextTest6 {

	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v6.xml");
		IPetStoreService petStore = (IPetStoreService)ctx.getBean("petStore");
	
		petStore.placeOrder();
		
		List<String> msgs = MessageTracker.getMsgs();
		
		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));	
		Assert.assertEquals("place order", msgs.get(1));	
		Assert.assertEquals("commit tx", msgs.get(2));	
	}

	@Before
	public void setUp(){
		MessageTracker.clearMsgs();
	}
	
	
}
