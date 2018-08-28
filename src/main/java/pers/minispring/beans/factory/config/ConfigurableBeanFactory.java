package pers.minispring.beans.factory.config;

/**
 * @author songbao.yang
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader classLoader);
}
