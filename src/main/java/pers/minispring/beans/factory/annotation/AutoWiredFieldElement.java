package pers.minispring.beans.factory.annotation;

import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.config.DependencyDescriptor;
import pers.minispring.beans.factory.support.DefaultBeanFactory;
import pers.minispring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author songbao.yang
 */
public class AutoWiredFieldElement extends InjectionElement {

    private boolean required;

    public AutoWiredFieldElement(Field field, boolean required, DefaultBeanFactory factory) {
        super(field, factory);
        this.required = required;
    }

    @Override
    public void inject(Object target) {
        Field field = this.getField();

        try {
            DependencyDescriptor descriptor = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(descriptor);
            if (value != null){
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable throwable){
            throw new BeanCreationException("Can not autowired field: " + field, throwable);
        }
    }

    private Field getField() {
        return (Field)member;
    }
}
