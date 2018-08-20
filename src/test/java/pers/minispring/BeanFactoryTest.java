package pers.minispring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.BeanDefinitionStoreException;
import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

    private DefaultBeanFactory factory = null;
    private XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }


    @Test
    public void testGetBean(){

        reader.loadBeanDefinition("petstore-v1.xml");

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        assertEquals("pers.minispring.PetStoreService", beanDefinition.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");

        assertNotNull(petStoreService);
    }

    @Test
    public void testInvalidBean() {

        reader.loadBeanDefinition("petstore-v1.xml");

        try {
            factory.getBean("invalidBean");
        }catch (BeanCreationException e){
            return;
        }
        Assert.fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXML() {

        try {
            reader.loadBeanDefinition("xxxx.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
