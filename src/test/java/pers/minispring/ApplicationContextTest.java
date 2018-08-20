package pers.minispring;

import org.junit.Test;
import pers.minispring.context.ApplicationContext;
import pers.minispring.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

public class ApplicationContextTest {

    @Test
    public void testGetBean(){
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService)context.getBean("petStore");

        assertNotNull(petStoreService);
    }
}
