package utilsBrowser.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationProvider {
    private static final Log logger = LogFactory.getLog(ConfigurationProvider.class);
    private Properties properties;

    private Properties getProperties(String environment) throws IOException {
        String env = System.getProperty("ENV", environment);
        final Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/config/" + env + ".properties"));
        return properties;
    }

    public Configuration getConfiguration(String environment) {
        try {
            properties = getProperties(environment);
        } catch (IOException e) {
            logger.debug("No such environment: " + environment);
            e.printStackTrace();
        }
        return new Configuration(properties);
    }
}
