package utilsBrowser.configuration;

import java.util.Properties;

public class Configuration {
    private String url;
    private String userName;
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Configuration(Properties properties) {
        this.url = properties.getProperty("url");
        this.userName = properties.getProperty("userName");
        this.password = properties.getProperty("password");
    }
}
