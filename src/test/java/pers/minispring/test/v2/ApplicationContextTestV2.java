package pers.minispring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.context.ApplicationContext;
import pers.minispring.context.support.ClassPathXmlApplicationContext;
import pers.minispring.dao.v2.AccountDao;
import pers.minispring.dao.v2.ItemDao;
import pers.minispring.service.v2.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

        assertNotNull(petStoreService);

        assertNotNull(petStoreService.getAccountDao());
        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);

        assertNotNull(petStoreService.getItemDao());
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);

        assertEquals("yangsongbao", petStoreService.getOwner());

        assertEquals(11, petStoreService.getVersion());
    }


}
