package stepDefinition;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import page.BasePage;
import page.LoginPage;
import page.partsAndSupplies.CompressorsPage;
import utilsBrowser.Excel.Excel;
import utilsBrowser.browserSetting.*;
import utilsBrowser.configuration.Configuration;
import utilsBrowser.configuration.ConfigurationProvider;

import java.util.HashMap;
import java.util.Map;

import static javax.security.auth.login.Configuration.getConfiguration;

public abstract class AbstractTest {
    private static final Log logger = LogFactory.getLog(BasePage.class);

    public static final String environment = getEnvironment();
    private static final String browser = getBrowser();
    protected WebDriver driver;

    protected static ConfigurationProvider configurationProvider = new ConfigurationProvider();
    protected Configuration configuration = configurationProvider.getConfiguration(environment);

    protected Excel excel = new Excel();
    protected BasePage basePage;
    protected LoginPage loginPage;
    protected CompressorsPage compressorsPage;

    public WebDriver getDriver() {
        return driver;
    }

    private static String getEnvironment() {
        return System.getProperty("environment");
    }

    private void getBrowser(Map<String, BrowserSettings> props) {
        driver = BrowserFactory.getBrowser(props);
    }

    private BrowserSettings getBrowserSettings() {
        BrowserSettings browserSettings;
        switch (browser) {
            case "chrome":
                browserSettings = new ChromeSettings();
                break;
            case "firefox":
                browserSettings = new FirefoxSettings();
                break;
            case "ie":
                browserSettings = new InternetExplorerSettings();
                break;
            default:
                browserSettings = new ChromeSettings();
        }
        return browserSettings;
    }

    public void setUp() {
        BrowserSettings browserSettings = getBrowserSettings();
        setUp(new HashMap<String, BrowserSettings>() {{
            put(browser, browserSettings);
        }});
    }

    public void setUp(Map<String, BrowserSettings> props) {
        getBrowser(props);
        getConfiguration();
        openBrowser();
        maximizeBrowser();
        initializePage();
    }

    void initializePage() {
        basePage = new BasePage(driver);
    }

    private void openBrowser() {
        driver.get(configuration.getUrl());
    }

    private static String getBrowser() {
        return System.getProperty("browser");
    }

    private void maximizeBrowser() {
        maximize();
        logger.info("Browser resolution is: " + getSize());
    }

    private Dimension getSize() {
        return driver.manage().window().getSize();
    }

    private void maximize() {
        driver.manage().window().maximize();
        logger.info("Current window maximized");
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
