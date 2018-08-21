package pers.minispring.context.support;

import pers.minispring.core.io.FileSystemResource;
import pers.minispring.core.io.Resource;

/**
 * @author songbao.yang
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {


    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
