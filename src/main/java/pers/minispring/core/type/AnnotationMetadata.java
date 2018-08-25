package pers.minispring.core.type;

import pers.minispring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author songbao.yang
 */
public interface AnnotationMetadata extends ClassMetadata {

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
