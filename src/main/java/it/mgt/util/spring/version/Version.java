package it.mgt.util.spring.version;

public class Version {

    private String major;
    private String minor;
    private String maintenance;

    public Version() {
    }

    public Version(String major, String minor, String maintenance) {
        this.major = major;
        this.minor = minor;
        this.maintenance = maintenance;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }
}
