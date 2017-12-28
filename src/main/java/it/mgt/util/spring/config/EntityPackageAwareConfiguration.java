package it.mgt.util.spring.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class EntityPackageAwareConfiguration implements ApplicationContextAware {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(EntityPackageAwareConfiguration.class);
    
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
    
    public String[] getEntityPackages(String persistenceUnit) {
        Map<String, Object> beansMap = applicationContext.getBeansWithAnnotation(EntityPackage.class);
        String[] packages = beansMap.values()
                .stream()
                .map(Object::getClass)
                .map(c -> getAnnotation(c, EntityPackage.class))
                .filter(ep -> ep != null)
                .filter(ep -> ep.persistenceUnit().equals(persistenceUnit))
                .map(EntityPackage::value)
                .flatMap(Arrays::stream)
                .distinct()
                .toArray(String[]::new);
        
        LOGGER.trace("Entity packages found: " + Arrays.toString(packages));
        
        return packages;
    }
    
    public String[] getEntityPackages() {
        return getEntityPackages("");
    }

}
