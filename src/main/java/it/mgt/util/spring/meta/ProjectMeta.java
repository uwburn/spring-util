package it.mgt.util.spring.meta;

public class ProjectMeta {

    private String groupId;
    private String artifactId;
    private String version;
    private String buildTimestamp;

    public ProjectMeta() {
    }

    public ProjectMeta(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public ProjectMeta(String groupId, String artifactId, String version, String buildTimestamp) {
        this(groupId, artifactId, version);
        this.buildTimestamp = buildTimestamp;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuildTimestamp() {
        return buildTimestamp;
    }

    public void setBuildTimestamp(String buildTimestamp) {
        this.buildTimestamp = buildTimestamp;
    }
}
