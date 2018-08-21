package pers.minispring.core.io;

import pers.minispring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author songbao.yang
 */
public class ClassPathResource implements Resource {

    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws Exception {
        InputStream inputStream = this.classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException(path + " cannot be opened");
        }
        return inputStream;
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
