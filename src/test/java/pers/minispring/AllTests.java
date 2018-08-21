package pers.minispring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class,
        ResourceTest.class})
public class AllTests {

}
