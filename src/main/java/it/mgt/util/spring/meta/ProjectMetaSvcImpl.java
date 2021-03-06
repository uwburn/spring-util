package it.mgt.util.spring.meta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectMetaSvcImpl implements ProjectMetaSvc {

    Logger logger = LoggerFactory.getLogger(ProjectMetaSvcImpl.class);

    private String metaFileName;

    private String groupId;
    private String artifactId;
    private String version;

    public ProjectMetaSvcImpl() {
        metaFileName = "meta.properties";
    }   

    public ProjectMetaSvcImpl(String metaFileName) {
        this.metaFileName = metaFileName;
    }

    public String getMetaFileName() {
        return metaFileName;
    }

    public void setMetaFileName(String metaFileName) {
        this.metaFileName = metaFileName;
    }

    @PostConstruct
    public void load() {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaFileName)) {
            Properties properties = new Properties();
            properties.load(is);

            groupId = properties.getProperty("project.groupId", "");
            artifactId = properties.getProperty("project.artifactId", "");
            version = properties.getProperty("project.version", "");
        } catch (IOException e) {
            logger.warn("Unable to load meta file", e);
        }
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public ProjectMeta getProjectMeta() {
        return new ProjectMeta(groupId, artifactId, version);
    }

}
