package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);

    void registryBeanDefinition(String beanID, BeanDefinition beanDefinition);
}
