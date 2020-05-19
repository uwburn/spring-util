package it.mgt.util.spring.log4j;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.OptionConverter;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Deprecated
public class Log4JRefresh {

    private URI configFileUri;
    private File configFile;

    // Get the properties file as Log4J does
    private static URI getLog4jConfig() {
        String override = OptionConverter.getSystemProperty("log4j.defaultInitOverride", null);
        if (override == null || "false".equalsIgnoreCase(override)) {
            String configurationOptionStr = OptionConverter.getSystemProperty("log4j.configuration", null);

            URL url;

            if (configurationOptionStr == null) {
                url = Loader.getResource("log4j.xml");
                if (url == null) {
                    url = Loader.getResource("log4j.properties");
                }
            } else {
                try {
                    url = new URL(configurationOptionStr);
                } catch (MalformedURLException ex) {
                    url = Loader.getResource(configurationOptionStr);
                }
            }
            try {
                return url.toURI();
            } catch (URISyntaxException e) {
                return null;
            }
        } else {
            try {
                return Loader.getResource(override).toURI();
            } catch (URISyntaxException e) {
                return null;
            }
        }
    }

    @PostConstruct
    public void postConstruct() {
        configFileUri = getLog4jConfig();
        configFile = new File(configFileUri);

        configure();
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void configure() {
        if (configFile == null)
            return;

        PropertyConfigurator.configure(configFile.getAbsolutePath());
    }

}
