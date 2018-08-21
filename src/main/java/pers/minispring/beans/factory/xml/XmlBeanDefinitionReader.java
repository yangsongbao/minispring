package pers.minispring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.BeanDefinitionStoreException;
import pers.minispring.beans.factory.support.BeanDefinitionRegistry;
import pers.minispring.beans.factory.support.GenericBeanDefiniton;
import pers.minispring.core.io.Resource;

import java.io.InputStream;
import java.util.Iterator;

/**
 * @author songbao.yang
 */
public class XmlBeanDefinitionReader {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource) {

        try (InputStream inputStream = resource.getInputStream()) {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            //<beans>
            Element root = document.getRootElement();
            Iterator iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefiniton(id, beanClassName);
                registry.registryBeanDefinition(id, beanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("parsing XML document fail", e);
        }
    }
}
