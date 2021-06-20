package utilsBrowser.browserSetting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

public interface BrowserSettings {

    DesiredCapabilities getDesiredCapabilities();
    HashMap<String, Object> getPreferences();
    WebDriver getDriver();

}
