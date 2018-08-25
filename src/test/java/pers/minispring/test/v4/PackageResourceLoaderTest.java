package pers.minispring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.core.io.Resource;
import pers.minispring.core.io.support.PackageResourceLoader;

import java.io.IOException;

public class PackageResourceLoaderTest {


    @Test
    public void testGetResource() throws IOException {
        PackageResourceLoader resourceLoader = new PackageResourceLoader();
        Resource[] resources = resourceLoader.getResources("pers.minispring.dao.v4");
        Assert.assertEquals(2, resources.length);
    }
}
