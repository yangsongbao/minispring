package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.BeansException;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.BeanFactory;
import pers.minispring.beans.factory.FactoryBean;
import pers.minispring.beans.factory.config.RuntimeBeanReference;
import pers.minispring.beans.factory.config.TypedStringValue;

/**
 * @author songbao.yang
 */
public class BeanDefinitionValueResolver {

    private AbstractBeanFactory beanFactory;

    public BeanDefinitionValueResolver(AbstractBeanFactory factory) {
        this.beanFactory = factory;
    }


    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            String beanName = reference.getBeanName();
            Object bean = beanFactory.getBean(beanName);
            return bean;
        }
        if (value instanceof TypedStringValue) {
            TypedStringValue stringValue = (TypedStringValue) value;
            return stringValue.getValue();
        } else if (value instanceof BeanDefinition) {
            // Resolve plain BeanDefinition, without contained name: use dummy name.
            BeanDefinition bd = (BeanDefinition) value;

            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));
            return resolveInnerBean(innerBeanName, bd);
        }
        else{
            return value;
        }
    }

    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBd) {

        try {

            Object innerBean = this.beanFactory.createBean(innerBd);

            if (innerBean instanceof FactoryBean) {
                try {
                    return ((FactoryBean<?>)innerBean).getObject();
                } catch (Exception e) {
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
                }
            }
            else {
                return innerBean;
            }
        }
        catch (BeansException ex) {
            throw new BeanCreationException(
                    innerBeanName,
                    "Cannot create inner bean '" + innerBeanName + "' " +
                            (innerBd != null && innerBd.getBeanClassName() != null ? "of type [" + innerBd.getBeanClassName() + "] " : "")
                    , ex);
        }
    }
}
