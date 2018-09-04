package pers.minispring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.factory.annotation.AutoWiredFieldElement;
import pers.minispring.beans.factory.annotation.AutowiredAnnotationProcessor;
import pers.minispring.beans.factory.annotation.InjectionElement;
import pers.minispring.beans.factory.annotation.InjectionMetadata;
import pers.minispring.beans.factory.config.DependencyDescriptor;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.dao.v4.AccountDao;
import pers.minispring.dao.v4.ItemDao;
import pers.minispring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.List;

public class AutowiredAnnotationProcessorTest {
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();
    DefaultBeanFactory beanFactory = new DefaultBeanFactory() {
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if (descriptor.getDependencyType().equals(AccountDao.class)) {
                return accountDao;
            }
            if (descriptor.getDependencyType().equals(ItemDao.class)) {
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };


    @Test
    public void testGetInjectionMetadata() {

        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements, "accountDao");
        assertFieldExists(elements, "itemDao");

        PetStoreService petStore = new PetStoreService();

        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }

    private void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for (InjectionElement ele : elements) {
            AutoWiredFieldElement fieldEle = (AutoWiredFieldElement) ele;
            Field f = fieldEle.getField();
            if (f.getName().equals(fieldName)) {
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }

}
