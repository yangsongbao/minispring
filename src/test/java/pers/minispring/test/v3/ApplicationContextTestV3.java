package pers.minispring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.context.support.ClassPathXmlApplicationContext;
import pers.minispring.service.v3.PetStoreService;

public class ApplicationContextTestV3 {

    @Test
    public void testGetBeanProperty() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertEquals(1, petStoreService.getVersion());
    }
}
