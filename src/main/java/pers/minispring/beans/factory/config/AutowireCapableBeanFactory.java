package pers.minispring.beans.factory.config;

import pers.minispring.beans.factory.BeanFactory;

/**
 * @author songbao.yang
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);
}
