package pers.minispring.beans.factory.annotation;

import pers.minispring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author songbao.yang
 */
public abstract class InjectionElement {

    protected Member member;
    protected AutowireCapableBeanFactory factory;

    public InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);

}
