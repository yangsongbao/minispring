package pers.minispring.core.type.classsreading;

import pers.minispring.core.io.Resource;
import pers.minispring.core.type.AnnotationMetadata;
import pers.minispring.core.type.ClassMetadata;

public interface MetadataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();

}
