package pers.minispring.test.v2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pers.minispring.beans.factory.config.RuntimeBeanReference;
import pers.minispring.beans.factory.config.TypedStringValue;
import pers.minispring.beans.factory.support.BeanDefinitionValueResolver;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.dao.v2.AccountDao;

public class BeanDefinitionValueResolverTest {

    private DefaultBeanFactory factory;
    private XmlBeanDefinitionReader reader;
    private BeanDefinitionValueResolver resolver;

    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));
        resolver = new BeanDefinitionValueResolver(factory);
    }

    @Test
    public void testResolveRuntimeBeanReference(){
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue(){
        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);

        Assert.assertNotNull(value);
        Assert.assertEquals(value, "test");
    }

}
