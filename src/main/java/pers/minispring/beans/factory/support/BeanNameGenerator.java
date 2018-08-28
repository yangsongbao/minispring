package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;

/**
 * @author songbao.yang
 */
public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition sbd, BeanDefinitionRegistry registry);
}
