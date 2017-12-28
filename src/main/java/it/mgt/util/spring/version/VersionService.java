package it.mgt.util.spring.version;

public interface VersionService {
    
    String getMajorVersionNumber();

    String getMinorVersionNumber();

    String getMaintenanceVersionNumber();
    
    Version getVersion();
    
}
