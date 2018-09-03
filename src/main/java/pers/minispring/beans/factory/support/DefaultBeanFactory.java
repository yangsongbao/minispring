package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.PropertyValue;
import pers.minispring.beans.SimpleTypeConverter;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.NoSuchBeanDefinitionException;
import pers.minispring.beans.factory.config.ConfigurableBeanFactory;
import pers.minispring.beans.factory.config.DependencyDescriptor;
import pers.minispring.util.ClassUtils;

import java.beans.BeanInfo;
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

    private ConstructorResolver constructorResolver;


    public DefaultBeanFactory() {
        converter = new SimpleTypeConverter();
        constructorResolver = new ConstructorResolver(this);
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

        if (beanDefinition.isSingleton()) {
            Object singleton = this.getSingleton(beanID);
            if (singleton == null) {
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanID, singleton);
            }
            return singleton;
        }

        return createBean(beanDefinition);

    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = this.getBeanDefinition(name);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Object bean = instantiateBean(beanDefinition);
        populateBean(beanDefinition, bean);
        return bean;
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();

        if (propertyValues == null || propertyValues.size() == 0) {
            return;
        }

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue propertyValue : propertyValues) {
                String propertyName = propertyValue.getName();
                Object originalValue = propertyValue.getValue();
                Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor : propertyDescriptors) {
                    if (propertyName.equals(descriptor.getName())) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, descriptor.getPropertyType());
                        descriptor.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", e);
        }

    }

    private Object instantiateBean(BeanDefinition beanDefinition) {
        if (beanDefinition.hasConstructorArgumentValues()) {
            return this.constructorResolver.autoWireConstructor(beanDefinition);
        }

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

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : this.beanDefinitionMap.values()) {
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(bd.getID());
            }
        }
        return null;
    }

    public void resolveBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass()) {
            return;
        } else {
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:" + bd.getBeanClassName());
            }
        }
    }
}
