package pers.minispring.beans.factory.annotation;


import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.config.AutowireCapableBeanFactory;
import pers.minispring.beans.factory.config.DependencyDescriptor;
import pers.minispring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author songbao.yang
 */
public class AutoWiredFieldElement extends InjectionElement {
    boolean required;

    public AutoWiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) this.member;
    }

    @Override
    public void inject(Object target) {

        Field field = this.getField();
        try {
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(desc);

            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }

}
