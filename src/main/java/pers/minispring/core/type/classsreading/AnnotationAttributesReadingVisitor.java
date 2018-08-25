package pers.minispring.core.type.classsreading;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import pers.minispring.core.annotation.AnnotationAttributes;

import java.util.Map;

/**
 * @author songbao.yang
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(
            String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(Opcodes.ASM5);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public void visit(String name, Object value) {
        this.attributes.put(name, value);
    }

    @Override
    public final void visitEnd(){
        this.attributesMap.put(this.annotationType, this.attributes);
    }
}
