package pers.minispring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.core.annotation.AnnotationAttributes;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.core.type.AnnotationMetadata;
import pers.minispring.core.type.classsreading.MetadataReader;
import pers.minispring.core.type.classsreading.SimpleMetadataReader;
import pers.minispring.stereotype.Component;

import java.io.IOException;


public class MetadataReaderTest {

    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("pers/minispring/service/v4/PetStoreService.class");

        MetadataReader reader = new SimpleMetadataReader(resource);
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(annotationMetadata.hasAnnotation(annotation));

        AnnotationAttributes attributes = annotationMetadata.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));
        Assert.assertFalse(annotationMetadata.isAbstract());
        Assert.assertFalse(annotationMetadata.isFinal());
        Assert.assertEquals("pers.minispring.service.v4.PetStoreService", annotationMetadata.getClassName());

    }
}
