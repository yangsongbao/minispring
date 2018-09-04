package pers.minispring.beans.factory.support;


import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.config.ConfigurableBeanFactory;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
	protected abstract Object createBean(BeanDefinition bd) throws BeanCreationException;
}
