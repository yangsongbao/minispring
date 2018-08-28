package pers.minispring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import pers.minispring.test.v1.V1AllTests;
import pers.minispring.test.v2.V2AllTests;
import pers.minispring.test.v3.V3AllTests;
import pers.minispring.test.v4.V4AllTests;

@RunWith(Suite.class)
@SuiteClasses({
        V1AllTests.class,
        V2AllTests.class,
        V3AllTests.class,
        V4AllTests.class})
public class AllTests {

}
