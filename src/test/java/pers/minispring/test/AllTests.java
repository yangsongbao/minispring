package pers.minispring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import pers.minispring.test.v1.V1AllTests;
import pers.minispring.test.v2.V2AllTests;

@RunWith(Suite.class)
@SuiteClasses({
        V1AllTests.class,
        V2AllTests.class})
public class AllTests {

}
