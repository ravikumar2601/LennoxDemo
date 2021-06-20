package utilsBrowser.browserSetting;

import org.openqa.selenium.WebDriver;

import java.util.Map;

public class BrowserFactory {
    public static final String BROWSER = System.getProperty("browser").toLowerCase();

    public static WebDriver getBrowser(Map<String, BrowserSettings> props) {
        return props.get(BROWSER).getDriver();
    }
}