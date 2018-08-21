package pers.minispring.beans.factory.config;

/**
 * @author songbao.yang
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
