package pers.minispring.context.support;

import pers.minispring.core.io.ClassPathResource;
import pers.minispring.core.io.Resource;

/**
 * @author songbao.yang
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path, this.getBeanClassLoader());
    }

}
