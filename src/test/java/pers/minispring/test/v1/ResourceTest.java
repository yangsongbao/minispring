package pers.minispring.test.v1;

import org.junit.Test;
import pers.minispring.core.io.ClassPathResource;
import pers.minispring.core.io.FileSystemResource;
import pers.minispring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class ResourceTest {

    @Test
    public void testClassPathResource() throws IOException {
        Resource resource = new ClassPathResource("petstore-v1.xml");

        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            assertNotNull(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws IOException {

        URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
        Resource resource = new FileSystemResource(location.getPath() + "petstore-v1.xml");

        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            assertNotNull(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
