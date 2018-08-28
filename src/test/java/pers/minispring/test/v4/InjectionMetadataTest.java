package pers.minispring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.factory.annotation.AutoWiredFieldElement;
import pers.minispring.beans.factory.annotation.InjectionElement;
import pers.minispring.beans.factory.annotation.InjectionMetadata;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.dao.v4.AccountDao;
import pers.minispring.dao.v4.ItemDao;
import pers.minispring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class InjectionMetadataTest {

    @Test
    public void testInjection() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v4.xml"));

        LinkedList<InjectionElement> elements = new LinkedList<>();

        {
            Field accountDaoFiled = PetStoreService.class.getDeclaredField("accountDao");
            InjectionElement injectionElement = new AutoWiredFieldElement(accountDaoFiled, true, factory);
            elements.add(injectionElement);
        }

        {
            Field itemDaoFiled = PetStoreService.class.getDeclaredField("itemDao");
            InjectionElement injectionElement = new AutoWiredFieldElement(itemDaoFiled, true, factory);
            elements.add(injectionElement);
        }

        InjectionMetadata metadata = new InjectionMetadata(PetStoreService.class, elements);

        PetStoreService petStoreService = new PetStoreService();

        metadata.inject(petStoreService);

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);

        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);
    }
}
