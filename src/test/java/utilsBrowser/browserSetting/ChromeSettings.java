package utilsBrowser.browserSetting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.logging.Level;

public class ChromeSettings implements BrowserSettings {
    private DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
    private ChromeOptions chromeOptions = new ChromeOptions();
    private HashMap<String, Object> chromePrefs = new HashMap<>();
    private LoggingPreferences loggingprefs = new LoggingPreferences();


    @Override
    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

    @Override
    public HashMap<String, Object> getPreferences() {
        return chromePrefs;
    }

    public ChromeOptions getChromeOptions() {
        return chromeOptions;
    }

    ChromeOptions addAll() {
        getPreferences().put("profile.default_content_settings.popups", 0);
        if (!getPreferences().containsKey("download.default_directory")) {
            getPreferences().put("download.default_directory", Utils.PATH_TO_DOWNLOAD);
        }
        getPreferences().put("safebrowsing.enabled", "true");
        chromeOptions.setExperimentalOption("prefs", getPreferences());
        loggingprefs.enable(LogType.BROWSER, Level.ALL);
        getDesiredCapabilities().setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        getDesiredCapabilities().setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        return chromeOptions.merge(getDesiredCapabilities());
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager.chromedriver().setup();

        return new ChromeDriver(addAll());
    }
}
