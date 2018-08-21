package pers.minispring.beans.factory.config;

import pers.minispring.beans.factory.BeanFactory;

/**
 * @author songbao.yang
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader classLoader);
}
