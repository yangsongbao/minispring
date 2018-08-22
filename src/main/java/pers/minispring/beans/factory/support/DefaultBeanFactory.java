package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.PropertyValue;
import pers.minispring.beans.SimpleTypeConverter;
import pers.minispring.beans.TypeConverter;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.config.ConfigurableBeanFactory;
import pers.minispring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author songbao.yang
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private ClassLoader beanClassLoader;

    private SimpleTypeConverter converter;


    public DefaultBeanFactory() {
        converter = new SimpleTypeConverter();
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public void registryBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID, beanDefinition);
    }

    @Override
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanID);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }

        if (beanDefinition.isSingleton()){
            Object singleton = this.getSingleton(beanID);
            if (singleton == null){
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanID, singleton);
            }
            return singleton;
        }

        return createBean(beanDefinition);

    }

    private Object createBean(BeanDefinition beanDefinition){
        Object bean = instantiateBean(beanDefinition);
        populateBean(beanDefinition, bean);
        return bean;
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();

        if (propertyValues == null || propertyValues.size() == 0){
            return;
        }

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue propertyValue : propertyValues){
                String propertyName = propertyValue.getName();
                Object originalValue = propertyValue.getValue();
                Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor : propertyDescriptors){
                    if (propertyName.equals(descriptor.getName())){
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, descriptor.getPropertyType());
                        descriptor.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("", e);
        }

    }

    private Object instantiateBean(BeanDefinition beanDefinition){
        ClassLoader loader = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();

        try {
            Class<?> aClass = loader.loadClass(beanClassName);
            //必须有无参构造函数
            return aClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
        }
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }
}
