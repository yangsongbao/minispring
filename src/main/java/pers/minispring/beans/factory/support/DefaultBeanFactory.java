package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.config.ConfigurableBeanFactory;
import pers.minispring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author songbao.yang
 */
public class DefaultBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private ClassLoader beanClassLoader;

    public DefaultBeanFactory() {
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
        ClassLoader loader = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();

        try {
            Class<?> aClass = loader.loadClass(beanClassName);
            //必须有无参构造函数
            return aClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " fail", e);
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
