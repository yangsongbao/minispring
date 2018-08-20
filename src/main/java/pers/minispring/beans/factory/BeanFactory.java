package pers.minispring.beans.factory;

import pers.minispring.beans.BeanDefinition;

public interface BeanFactory {

    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);
}
