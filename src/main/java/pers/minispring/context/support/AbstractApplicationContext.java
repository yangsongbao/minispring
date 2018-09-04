package pers.minispring.context.support;

import pers.minispring.aop.aspectj.AspectJAutoProxyCreator;
import pers.minispring.beans.factory.NoSuchBeanDefinitionException;
import pers.minispring.beans.factory.annotation.AutowiredAnnotationProcessor;
import pers.minispring.beans.factory.config.ConfigurableBeanFactory;
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
        registerBeanPostProcessors(factory);
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

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
        {
            AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
            postProcessor.setBeanFactory(beanFactory);
            beanFactory.addBeanPostProcessor(postProcessor);
        }
        {
            AspectJAutoProxyCreator postProcessor = new AspectJAutoProxyCreator();
            postProcessor.setBeanFactory(beanFactory);
            beanFactory.addBeanPostProcessor(postProcessor);
        }
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
