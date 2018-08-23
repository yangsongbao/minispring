package pers.minispring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.support.ConstructorResolver;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.service.v3.PetStoreService;

public class ConstructorResolverTest {



    @Test
    public void testConstructorResolver(){

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v3.xml"));

        BeanDefinition definition = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreService petStoreService = (PetStoreService)resolver.autoWireConstructor(definition);

        Assert.assertEquals(1, petStoreService.getVersion());

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }

}
