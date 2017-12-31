package it.mgt.util.spring.version;

public interface ProjectMetaSvc {
    
    String getGroupId();

    String getArtifactId();

    String getVersion();
    
    ProjectMeta getProjectMeta();
    
}
