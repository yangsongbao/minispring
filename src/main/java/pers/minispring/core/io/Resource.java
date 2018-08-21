package pers.minispring.core.io;

import java.io.InputStream;

/**
 * @author songbao.yang
 */
public interface Resource {

    InputStream getInputStream() throws Exception;

    String getDescription();
}
