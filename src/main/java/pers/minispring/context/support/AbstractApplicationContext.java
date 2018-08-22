package pers.minispring.context.support;

import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import pers.minispring.context.ApplicationContext;
import pers.minispring.core.io.Resource;
import pers.minispring.util.ClassUtils;

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
        reader.loadBeanDefinition(resource);
        factory.setBeanClassLoader(classLoader);
    }

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected abstract Resource getResourceByPath(String path);

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }
}
