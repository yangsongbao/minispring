package pers.minispring.beans.factory.annotation;

import pers.minispring.beans.BeanDefinition;
import pers.minispring.core.type.AnnotationMetadata;

/**
 * @author songbao.yang
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();
}
