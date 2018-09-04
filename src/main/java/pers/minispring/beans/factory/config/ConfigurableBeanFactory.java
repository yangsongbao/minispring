package pers.minispring.beans.factory.config;

import java.util.List;

/**
 * @author songbao.yang
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessors();
}
