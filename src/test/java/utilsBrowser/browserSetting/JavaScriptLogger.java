package utilsBrowser.browserSetting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JavaScriptLogger {
    public static final List<String> jsErrors = new ArrayList<>();

    public static void extractJSLogs(WebDriver driver) {

        if (BrowserFactory.BROWSER.equals("chrome")) {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                String str = new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage();
                jsErrors.add(str);
            }
        }

    }

    public static void cleanLogs() {
        jsErrors.clear();

    }
}
