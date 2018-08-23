package pers.minispring.beans.factory;

import pers.minispring.beans.BeansException;

public class BeanCreationException extends BeansException {

    private String beanName;

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(String message, Throwable cause, String beanName) {
        super(message, cause);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String message) {
        super("Error creating bean with name '" + beanName + "': " + message);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String message, ClassNotFoundException e) {
        super("Error creating bean with name '" + beanName + "': " + message, e);
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
