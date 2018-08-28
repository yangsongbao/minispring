package pers.minispring.context.annotation;


import pers.minispring.beans.factory.annotation.AnnotatedBeanDefinition;
import pers.minispring.beans.factory.support.GenericBeanDefinition;
import pers.minispring.core.type.AnnotationMetadata;

/**
 * @author songbao.yang
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;


    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();

        this.metadata = metadata;

        setBeanClassName(this.metadata.getClassName());
    }


    @Override
    public final AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
