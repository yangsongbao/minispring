package pers.minispring.test.v4;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV4.class,
        ClassPathBeanDefinitionScannerTest.class,
        ClassReaderTest.class,
        DependencyDescriptorTest.class,
        MetadataReaderTest.class,
        PackageResourceLoaderTest.class})
public class V4AllTests {
}
