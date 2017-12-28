package it.mgt.util.spring.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionServiceImpl implements VersionService {

    Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

    private String versionFileName;

    private String majorVersionNumber;
    private String minorVersionNumber;
    private String maintenanceVersionNumber;

    public VersionServiceImpl(String versionFileName) {
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

            majorVersionNumber = properties.getProperty("majorVersionNumber", "0");
            minorVersionNumber = properties.getProperty("minorVersionNumber", "0");
            maintenanceVersionNumber = properties.getProperty("maintenanceVersionNumber", "0");
        } catch (IOException e) {
            logger.warn("Unable to load version file", e);
        }
    }

    @Override
    public String getMajorVersionNumber() {
        return majorVersionNumber;
    }

    @Override
    public String getMinorVersionNumber() {
        return minorVersionNumber;
    }

    @Override
    public String getMaintenanceVersionNumber() {
        return maintenanceVersionNumber;
    }

    @Override
    public Version getVersion() {
        return new Version(majorVersionNumber, versionFileName, versionFileName);
    }

}
