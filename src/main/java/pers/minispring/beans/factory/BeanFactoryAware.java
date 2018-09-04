package pers.minispring.beans.factory;


import pers.minispring.beans.BeansException;

public interface BeanFactoryAware {

	/**
	 * Callback that supplies the owning factory to a bean instance.
	 * <p>Invoked after the population of normal bean properties
	 * but before an initialization callback such as
	 */
	void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}