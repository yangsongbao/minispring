package pers.minispring.beans.factory;


import java.util.List;

/**
 * @author songbao.yang
 */
public interface BeanFactory {

    Object getBean(String beanID);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    List<Object> getBeansByType(Class<?> type);

}
