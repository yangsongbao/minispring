package pers.minispring.core.io;

import pers.minispring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author songbao.yang
 */
public class FileSystemResource implements Resource {

    private final String path;
    private final File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "Path must be null");
        this.file = new File(path);
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return new FileInputStream(this.path);
    }

    @Override
    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }
}
