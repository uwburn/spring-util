package it.mgt.util.spring.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class EntityPackageAwareConfiguration implements ApplicationContextAware {
    
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }    

    private <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> annotationClass) {
        A a = clazz.getAnnotation(annotationClass);
        if (a != null)
            return a;

        clazz = clazz.getSuperclass();
        if (clazz == null)
            return null;

        return getAnnotation(clazz, annotationClass);
    }

    private Set<String> resolveEntityPackages(Class<?> conf) {
        Set<String> packages = new LinkedHashSet<>();

        EntityPackage ep = getAnnotation(conf, EntityPackage.class);
        if (ep != null)
            Collections.addAll(packages, ep.value());

        Import imp = getAnnotation(conf, Import.class);
        if (imp != null)
            for (Class<?> importedConf : imp.value())
                packages.addAll(resolveEntityPackages(importedConf));

        return packages;
    }
    
    public String[] getEntityPackages(String persistenceUnit) {
        Map<String, Object> beansMap = applicationContext.getBeansWithAnnotation(EntityPackage.class);
        return beansMap.values()
                .stream()
                .map(Object::getClass)
                .map(c -> getAnnotation(c, EntityPackage.class))
                .filter(ep -> ep != null)
                .filter(ep -> ep.persistenceUnit().equals(persistenceUnit))
                .map(EntityPackage::value)
                .flatMap(Arrays::stream)
                .distinct()
                .toArray(String[]::new);
    }
    
    public String[] getEntityPackages() {
        return getEntityPackages("");
    }

}
