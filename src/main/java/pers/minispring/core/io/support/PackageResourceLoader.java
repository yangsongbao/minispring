package pers.minispring.core.io.support;

import lombok.extern.slf4j.Slf4j;
import pers.minispring.core.io.FileSystemResource;
import pers.minispring.core.io.Resource;
import pers.minispring.util.Assert;
import pers.minispring.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author songbao.yang
 */
@Slf4j
public class PackageResourceLoader {

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage, "basePackage  must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(location);
        if (url == null) {
            log.warn("can not get resources from location: {}", location);
            return new Resource[0];
        }
        File rootDir = new File(url.getFile());

        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for (File file : matchingFiles) {
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    protected Set<File> retrieveMatchingFiles(File rootDir) throws IOException {
        if (!rootDir.exists()) {
            log.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            log.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            log.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                    "] because the application is not allowed to read the directory");
            return Collections.emptySet();
        }
        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    protected void doRetrieveMatchingFiles(File dir, Set<File> result) throws IOException {

        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            log.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
            return;
        }
        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (!content.canRead()) {
                    log.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                            "] because the application is not allowed to read the directory");
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
