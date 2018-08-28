package pers.minispring.beans.factory.xml;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.ConstructorArgument;
import pers.minispring.beans.PropertyValue;
import pers.minispring.beans.factory.BeanDefinitionStoreException;
import pers.minispring.beans.factory.config.RuntimeBeanReference;
import pers.minispring.beans.factory.config.TypedStringValue;
import pers.minispring.beans.factory.support.BeanDefinitionRegistry;
import pers.minispring.beans.factory.support.GenericBeanDefinition;
import pers.minispring.context.annotation.ClassPathBeanDefinitionScanner;
import pers.minispring.core.io.Resource;
import pers.minispring.util.StringUtils;

import java.io.InputStream;
import java.util.Iterator;

/**
 * @author songbao.yang
 */
@Slf4j
public class XmlBeanDefinitionReader {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE_ATTRIBUTE = "scope";

    private static final String PROPERTY_ELEMENT = "property";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String CONSTRUCTOR_ARG_ATTRIBUTE = "constructor-arg";
    private static final String TYPE_ATTRIBUTE = "type";
    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource) {

        try (InputStream inputStream = resource.getInputStream()) {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            //<beans>
            Element root = document.getRootElement();
            Iterator iterator = root.elementIterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String namespaceUri = element.getNamespaceURI();
                if(this.isDefaultNamespace(namespaceUri)){
                    parseDefaultElement(element);
                } else if(this.isContextNamespace(namespaceUri)){
                    parseComponentElement(element);
                }
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("parsing XML document fail", e);
        }
    }

    private void parseDefaultElement(Element element) {
        String id = element.attributeValue(ID_ATTRIBUTE);
        String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
        BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
        if (element.attribute(SCOPE_ATTRIBUTE) != null) {
            beanDefinition.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
        }
        parseConstructorArgElements(element, beanDefinition);
        parsePropertyElement(element, beanDefinition);
        registry.registryBeanDefinition(id, beanDefinition);
    }

    private void parseComponentElement(Element element) {
        String basePackages = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    private boolean isContextNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    private boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    private void parseConstructorArgElements(Element element, BeanDefinition beanDefinition) {
        Iterator iterator = element.elementIterator(CONSTRUCTOR_ARG_ATTRIBUTE);
        while (iterator.hasNext()) {
            Element next = (Element) iterator.next();
            parseConstructorArgElement(next, beanDefinition);
        }
    }

    private void parseConstructorArgElement(Element element, BeanDefinition beanDefinition) {
        String typeAttr = element.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = element.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(element, beanDefinition, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (!StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);
        }
        if (!StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);
        }
        beanDefinition.getConstructorArgument().addArgumentValue(valueHolder);
    }

    private void parsePropertyElement(Element element, BeanDefinition beanDefinition) {
        Iterator iterator = element.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()) {
            Element propertyElement = (Element) iterator.next();
            String propertyName = propertyElement.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                log.error("Tag 'property' nust have a 'name' attribute");
                continue;
            }
            Object val = parsePropertyValue(propertyElement, beanDefinition, propertyName);
            PropertyValue propertyValue = new PropertyValue(propertyName, val);
            beanDefinition.getPropertyValues().add(propertyValue);
        }
    }

    private Object parsePropertyValue(Element propertyElement, BeanDefinition beanDefinition, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "," :
                "<constructor-arg> element";

        boolean hasRefAttribute = (propertyElement.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (propertyElement.attribute(VALUE_ATTRIBUTE) != null);

        if (hasRefAttribute) {
            String refName = propertyElement.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                log.error(elementName + " contains empty 'ref' attribute");
            }
            return new RuntimeBeanReference(refName);
        } else if (hasValueAttribute) {
            return new TypedStringValue(propertyElement.attributeValue(VALUE_ATTRIBUTE));
        } else {
            //todo
            throw new RuntimeException(elementName + "must specify a ref or value");
        }
    }
}
