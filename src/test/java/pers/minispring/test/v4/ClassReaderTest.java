package pers.minispring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import pers.minispring.core.annotation.AnnotationAttributes;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.core.type.classsreading.AnnotationMetadataReadingVisitor;
import pers.minispring.core.type.classsreading.ClassMetadataReadingVisitor;

import java.io.IOException;

public class ClassReaderTest {

    @Test
    public void testGetClassMeatData() throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("pers/minispring/service/v4/PetStoreService.class");
        ClassReader classReader = new ClassReader(classPathResource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor(Opcodes.ASM5);

        classReader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("pers.minispring.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnotation() throws IOException{
        ClassPathResource resource = new ClassPathResource("pers/minispring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        String annotation = "pers.minispring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        Assert.assertEquals("petStore", attributes.get("value"));

    }
}
