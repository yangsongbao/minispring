package pers.minispring.core.type.classsreading;

import org.objectweb.asm.ClassReader;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.core.io.Resource;
import pers.minispring.core.type.AnnotationMetadata;
import pers.minispring.core.type.ClassMetadata;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author songbao.yang
 */
public class SimpleMetadataReader implements MetadataReader {

    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;


    public SimpleMetadataReader(ClassPathResource resource) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;

        try {
            classReader = new ClassReader(inputStream);
        } finally {
            inputStream.close();
        }

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);

        this.annotationMetadata = visitor;
        this.classMetadata = visitor;
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
