package pers.minispring.beans.factory.support;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.BeanDefinitionStoreException;
import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author songbao.yang
 */
public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {

        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(configFile)){
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            //<beans>
            Element root = document.getRootElement();
            Iterator iterator = root.elementIterator();
            while (iterator.hasNext()){
                Element element = (Element)iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefiniton(id, beanClassName);
                this.beanDefinitionMap.put(id, beanDefinition);
            }
        } catch (Exception e){
            throw new BeanDefinitionStoreException("parsing XML document fail", e);
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanID);
        if (beanDefinition == null){
            throw new BeanCreationException("Bean Definition does not exist");
        }
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();

        try {
            Class<?> aClass = loader.loadClass(beanClassName);
            //必须有无参构造函数
            return aClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " fail", e);
        }
    }
}
