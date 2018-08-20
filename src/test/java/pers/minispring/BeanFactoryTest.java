package pers.minispring;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.BeanDefinitionStoreException;
import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.support.DefaultBeanFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");

        assertEquals("pers.minispring.PetStoreService", beanDefinition.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)beanFactory.getBean("petStore");

        assertNotNull(petStoreService);
    }

    @Test
    public void testInvalidBean() {

        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            beanFactory.getBean("invalidBean");
        }catch (BeanCreationException e){
            return;
        }
        Assert.fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXML() {

        try {
            new DefaultBeanFactory("xxx-v1.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
