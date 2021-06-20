package utilsBrowser.browserSetting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class FirefoxSettings implements BrowserSettings {
    private DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
    private FirefoxOptions firefoxOptions;
    private HashMap<String, Object> firefoxPrefs = new HashMap<>();
    private FirefoxProfile firefoxProfile = new FirefoxProfile();

    @Override
    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

    @Override
    public HashMap<String, Object> getPreferences() {
        return firefoxPrefs;
    }

    private void initializeFirefoxOptions(DesiredCapabilities desiredCapabilities){
        firefoxOptions = new FirefoxOptions(desiredCapabilities);
    }

    private FirefoxOptions getFirefoxOptions() {
        return firefoxOptions;
    }

    private FirefoxProfile getFirefoxProfile() {
        return firefoxProfile;
    }

    private FirefoxOptions addAll() {
        getDesiredCapabilities().setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        getDesiredCapabilities().setCapability("marionette", true);
        getDesiredCapabilities().setCapability("binary", "src/test/resources/drivers/firefox-sdk/bin/firefox.exe");
        initializeFirefoxOptions(getDesiredCapabilities());
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("browser.download.dir", Utils.PATH_TO_DOWNLOAD);
        if (!getFirefoxProfile().getStringPreference("browser.download.dir", "browser.download.dir")
                .equals(new DirectoryUtil("temp").produceDirAndPath())) {
            getFirefoxProfile().setPreference("browser.download.dir", Utils.PATH_TO_DOWNLOAD);
        }
        getFirefoxOptions().setProfile(getFirefoxProfile());

        return getFirefoxOptions();
    }

    @Override
    public WebDriver getDriver() {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        return new FirefoxDriver(addAll());
    }
}
