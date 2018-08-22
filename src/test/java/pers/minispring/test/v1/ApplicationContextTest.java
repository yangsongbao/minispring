package pers.minispring.test.v1;

import org.junit.Test;
import pers.minispring.context.ApplicationContext;
import pers.minispring.context.support.ClassPathXmlApplicationContext;
import pers.minispring.context.support.FileSystemXmlApplicationContext;
import pers.minispring.service.v1.PetStoreService;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class ApplicationContextTest {


    @Test
    public void testGetBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");
        assertNotNull(petStoreService);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
        ApplicationContext context = new FileSystemXmlApplicationContext(location.getPath() + "petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");
        assertNotNull(petStoreService);
    }
}
