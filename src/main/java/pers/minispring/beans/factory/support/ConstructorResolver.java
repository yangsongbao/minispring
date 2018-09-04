package pers.minispring.beans.factory.support;

import lombok.extern.slf4j.Slf4j;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.ConstructorArgument;
import pers.minispring.beans.SimpleTypeConverter;
import pers.minispring.beans.factory.BeanCreationException;
import pers.minispring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author songbao.yang
 */
@Slf4j
public class ConstructorResolver {

    private final AbstractBeanFactory beanFactory;

    public ConstructorResolver(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autoWireConstructor(final BeanDefinition definition) {

        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass = null;

        try {
            beanClass = this.beanFactory.getBeanClassLoader().loadClass(definition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(definition.getID(), "Instantiation of bean failed, can't resolve class", e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument argument = definition.getConstructorArgument();

        SimpleTypeConverter converter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if (parameterTypes.length != argument.getArgumentCount()) {
                continue;
            }
            argsToUse = new Object[parameterTypes.length];

            boolean result = this.valuesMatchTypes(parameterTypes, argument.getArgumentValues(), argsToUse, resolver, converter);
            if (result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        if (constructorToUse == null) {
            throw new BeanCreationException(definition.getID(), "can not find a appropriate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(definition.getID(), "can not find a create instance using " + constructorToUse);
        }
    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes, List<ConstructorArgument.ValueHolder> argumentValues, Object[] argsToUse, BeanDefinitionValueResolver resolver, SimpleTypeConverter converter) {

        for (int i = 0; i < parameterTypes.length; i++) {

            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            //获取参数的值，可能是TypedStringValue, 也可能是RuntimeBeanReference
            Object value = valueHolder.getValue();

            try {
                //获得真正的值
                Object resolvedValue = resolver.resolveValueIfNecessary(value);
                //如果参数类型是 int, 但是值是字符串,例如"3",还需要转型
                //如果转型失败，则抛出异常。说明这个构造函数不可用
                Object convertedValue = converter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                log.error("", e);
                return false;
            }
        }
        return true;
    }

}
