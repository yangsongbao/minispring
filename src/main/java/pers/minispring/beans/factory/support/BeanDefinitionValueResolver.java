package pers.minispring.beans.factory.support;

import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.beans.factory.config.RuntimeBeanReference;
import pers.minispring.beans.factory.config.TypedStringValue;

/**
 * @author songbao.yang
 */
public class BeanDefinitionValueResolver {

    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory factory) {
        this.beanFactory = factory;
    }


    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference){
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            String beanName = reference.getBeanName();
            Object bean = beanFactory.getBean(beanName);
            return bean;
        } if (value instanceof TypedStringValue){
            TypedStringValue stringValue = (TypedStringValue)value;
            return stringValue.getValue();
        } else {
            throw new RuntimeException("the value " + value + " has not implemented");
        }
    }
}
