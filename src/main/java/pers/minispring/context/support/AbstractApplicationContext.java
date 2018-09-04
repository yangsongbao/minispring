package pers.minispring.context.support;

import pers.minispring.beans.factory.NoSuchBeanDefinitionException;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.context.ApplicationContext;
import pers.minispring.core.io.Resource;
import pers.minispring.util.ClassUtils;

import java.util.List;

/**
 * @author songbao.yang
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        this(configFile, ClassUtils.getDefaultClassLoader());
    }

    public AbstractApplicationContext(String configFile, ClassLoader classLoader) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(classLoader);
    }

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected abstract Resource getResourceByPath(String path);

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return this.factory.getType(name);
    }

    @Override
    public List<Object> getBeansByType(Class<?> type){
        return this.factory.getBeansByType(type);
    }
}
