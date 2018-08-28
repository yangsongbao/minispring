package pers.minispring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.factory.config.DependencyDescriptor;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.dao.v4.AccountDao;
import pers.minispring.service.v4.PetStoreService;

import java.lang.reflect.Field;

public class DependencyDescriptorTest {
    @Test
    public void testResolveDependency() throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v4.xml"));

        Field field = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(field, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }
}
