package pers.minispring.beans.factory.config;


import pers.minispring.beans.BeansException;

public interface BeanPostProcessor {

	Object beforeInitialization(Object bean, String beanName) throws BeansException;

	
	Object afterInitialization(Object bean, String beanName) throws BeansException;

}
