package pers.minispring.test.v5;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest5.class,
        BeanDefinitionTestV5.class,
        BeanFactoryTestV5.class,
        CglibAopProxyTest.class,
        CGLibTest.class,
        MethodLocatingFactoryTest.class,
        PointcutTest.class,
        ReflectiveMethodInvocationTest.class})
public class V5AllTests {
}
