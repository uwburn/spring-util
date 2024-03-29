package it.mgt.util.spring.meta;

public interface ProjectMetaSvc {
    
    String getGroupId();

    String getArtifactId();

    String getVersion();

    String getBuildTimestamp();
    
    ProjectMeta getProjectMeta();
    
}
