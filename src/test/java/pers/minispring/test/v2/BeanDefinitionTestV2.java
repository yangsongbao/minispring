package pers.minispring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.PropertyValue;
import pers.minispring.beans.factory.config.RuntimeBeanReference;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.core.io.ClassPathResource;

import java.util.List;

public class BeanDefinitionTestV2 {


    @Test
    public void testGetBeanDefinition(){

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition definition = factory.getBeanDefinition("petStore");

        List<PropertyValue> propertyValues = definition.getPropertyValues();

        Assert.assertEquals(propertyValues.size(), 4);

        {
            PropertyValue propertyValue = this.getPropertyValue("accountDao", propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue propertyValue = this.getPropertyValue("itemDao", propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> propertyValues) {
        for (PropertyValue propertyValue : propertyValues){
            if (propertyValue.getName().equals(name)){
                return propertyValue;
            }
        }
        return null;
    }

}
