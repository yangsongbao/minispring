package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);

    void registerBeanDefinition(String beanID, BeanDefinition beanDefinition);
}
