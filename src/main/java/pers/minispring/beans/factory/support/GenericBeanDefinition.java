package pers.minispring.beans.factory.support;

import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.ConstructorArgument;
import pers.minispring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songbao.yang
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;

    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues;
    private ConstructorArgument  constructorArgument = new ConstructorArgument();

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
        this.propertyValues = new ArrayList<>(5);
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return constructorArgument != null && !constructorArgument.isEmpty();
    }

    @Override
    public String getID() {
        return this.getID();
    }

}
