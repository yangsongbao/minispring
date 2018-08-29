package pers.minispring.context.annotation;

import lombok.extern.slf4j.Slf4j;
import pers.minispring.beans.BeanDefinition;
import pers.minispring.beans.factory.BeanDefinitionStoreException;
import pers.minispring.beans.factory.support.BeanDefinitionRegistry;
import pers.minispring.beans.factory.support.BeanNameGenerator;
import pers.minispring.core.io.Resource;
import pers.minispring.core.io.support.PackageResourceLoader;
import pers.minispring.core.type.classsreading.SimpleMetadataReader;
import pers.minispring.stereotype.Component;
import pers.minispring.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author songbao.yang
 */
@Slf4j
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String basePackageToScan) {

        String[] basePackages = StringUtils.tokenizeToStringArray(basePackageToScan, ",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.registryBeanDefinition(candidate.getID(), candidate);
            }
        }

    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            Resource[] resources = this.resourceLoader.getResources(basePackage);
            for (Resource resource : resources) {
                try {
                    SimpleMetadataReader reader = new SimpleMetadataReader(resource);
                    if (reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                        ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(reader.getAnnotationMetadata());
                        String beanName = this.beanNameGenerator.generateBeanName(beanDefinition, this.registry);
                        beanDefinition.setId(beanName);
                        candidates.add(beanDefinition);
                    }
                } catch (Throwable e) {
                    throw new BeanDefinitionStoreException("Failed to read candidate component class: " + resource, e);
                }
            }
        } catch (Throwable e) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", e);
        }
        return candidates;
    }
}
