package pers.minispring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.context.support.ClassPathXmlApplicationContext;
import pers.minispring.service.v4.PetStoreService;

public class ApplicationContextTestV4 {

    @Test
    public void testGetBeanProperty() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
