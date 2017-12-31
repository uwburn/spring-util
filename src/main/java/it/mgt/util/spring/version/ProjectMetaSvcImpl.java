package it.mgt.util.spring.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectMetaSvcImpl implements ProjectMetaSvc {

    Logger logger = LoggerFactory.getLogger(ProjectMetaSvcImpl.class);

    private String versionFileName;

    private String groupId;
    private String artifactId;
    private String version;

    public ProjectMetaSvcImpl() {
        versionFileName = "version.properties";
    }   

    public ProjectMetaSvcImpl(String versionFileName) {
        this.versionFileName = versionFileName;
    }

    public String getVersionFileName() {
        return versionFileName;
    }

    public void setVersionFileName(String versionFileName) {
        this.versionFileName = versionFileName;
    }

    @PostConstruct
    public void load() {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(versionFileName)) {
            Properties properties = new Properties();
            properties.load(is);

            groupId = properties.getProperty("project.groupId", "");
            artifactId = properties.getProperty("project.artifactId", "");
            version = properties.getProperty("project.version", "");
        } catch (IOException e) {
            logger.warn("Unable to load version file", e);
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
