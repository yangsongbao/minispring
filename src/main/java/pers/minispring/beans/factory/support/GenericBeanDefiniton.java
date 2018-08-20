package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;

public class GenericBeanDefiniton implements BeanDefinition {

    private String id;
    private String beanClassName;

    public GenericBeanDefiniton(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
