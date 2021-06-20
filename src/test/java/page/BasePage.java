package page;

import assertion.AbstractAssertion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.ofSeconds;

public class BasePage implements SeleniumUtils {

    private static final Log logger = LogFactory.getLog(BasePage.class);

    private static final int WAIT_UNTIL_TIMEOUT = 25;
    private static final int ANGULAR_WAIT = (System.getProperty("environment").contains("online")) ? 40 : 30;
    private static final String BASE_URL = "";

    public WebDriver driver;
    protected Actions action;
    public JavascriptExecutor jsExecutor;

    public BasePage(WebDriver driver) {
        action = new Actions(driver);
        PageFactory.initElements(getElementLocatorFactory(driver), this);
    }

    private WebDriver getElementLocatorFactory(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        return driver;
    }

    @Override
    public void waitUntil(ExpectedCondition<?> expectedCondition, int timeoutSeconds) {
        logger.info(": waiting " + timeoutSeconds + " s until : " + expectedCondition);
        new WebDriverWait(driver, timeoutSeconds).until(expectedCondition);
    }

    @Override
    public void waitUntil(ExpectedCondition<?> expectedCondition) {
        logger.info(": waiting " + WAIT_UNTIL_TIMEOUT + " s until expected condition " + expectedCondition + " is met.");
        waitUntil(expectedCondition, WAIT_UNTIL_TIMEOUT);
    }

    @Override
    public WebElement fluentWait(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(ofSeconds(30))
                .pollingEvery(ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        return wait.until(driver1 -> {
            logger.info(": waiting for element " + element
                    + " with locator : \" + locator + \" with timeout 30s polling every 1 s.");
            return element;
        });
    }

    @Override
    public void waitInSec(Integer sec) {
        logger.info(": waiting for " + sec + " with thread sleep for 1 sec");
        long milisec = sec.longValue() * 1000;
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveToElementAndForceClick(WebElement element) {
        logger.info(": move to element actions");
        action.moveToElement(element).click().build().perform();
    }

    @Override
    public void doubleClick(WebElement element) {
        logger.info(": move to element actions");
        action.doubleClick(element).perform();
    }

    @Override
    public void scrollToTheBottom() {
        logger.info(": scroll to the bottom of the screen using java script executor");
        jsExecutor.executeScript("window.scrollBy(0,+500)", "");
    }

    @Override
    public void scrollToTheTop() {
        logger.info(": scroll to the top of the screen using java script executor");
        jsExecutor.executeScript("window.scrollBy(0,-500)", "");
    }


    @Override
    public <T extends BasePage> T goToPreviousPage(Class<T> clazz) throws RuntimeException {
        logger.info(": go to previous page");
        driver.navigate().back();
        T page = null;
        try {
            page = clazz.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public BasePage switchToBrowserTab(int windowNumber) {
        logger.info(": switch to tab number: " + windowNumber);
        waitInSec(3);
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        if (windowHandles.size() > 1) {
            List<String> windowHandlesTemp;
            int i = 0;
            do {
                i++;
                windowHandlesTemp = new ArrayList<>(driver.getWindowHandles());
            } while (windowHandlesTemp.size() < windowNumber && i < 10);

            switchToWindow(windowHandlesTemp.get(windowNumber - 1));
        }
        return this;
    }

    @Override
    public void switchToWindow(String handle) {
        logger.info(": switch to window with handle: " + handle);
        try {
            driver.switchTo().window(handle);
        } catch (NoSuchWindowException e) {
            waitInSec(5);
            logger.info("Attempt to switch to window...");
            driver.switchTo().window(handle);
        }
    }

    @Override
    public void refresh() {
        logger.info("Refresh page");
        driver.navigate().refresh();
    }

    @Override
    public BasePage switchToTab(int windowNumber) {
        waitInSec(5);
        List<String> windowHandles;
        int i = 0;
        do {
            i++;
            windowHandles = new ArrayList<>(driver.getWindowHandles());
        } while (windowHandles.size() < windowNumber && i < 10);

        switchToWindow(windowHandles.get(windowNumber - 1));

        return this;
    }


    @Override
    public boolean isAt(String pageUrl) {
        logger.info(": method start");
        String actualUrl = getPageUrl();
        logger.info("Page Class: expected base url: " + pageUrl);
        logger.info("Page Class: actual url: " + actualUrl);
        return actualUrl.contains(pageUrl);
    }

    @Override
    public String getPageUrl() {
        logger.info(": Getting current URL.");
        waitInSec(3);
        return driver.getCurrentUrl();
    }

    @Override
    public String getBaseURL() {
        logger.info(": method start");
        return BASE_URL;
    }

    public <G extends BasePage, T extends AbstractAssertion<G>> T startAssertions(Class<T> clazz) throws RuntimeException {
        try {
            AbstractAssertion<G> assertion = clazz.newInstance();
            assertion.setPage((G) this);
            return (T) assertion;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error occurred during creating Assertions. ", e);
        }
    }

    @Override
    public <T extends BasePage> void acceptAlertIfPresent(Class<T> clazz) throws RuntimeException {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }
        T BasePage = null;
        try {
            BasePage = clazz.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends BasePage> void dismissAlertIfPresent(Class<T> clazz) throws RuntimeException {
        try {
            waitInSec(2);
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }
        T BasePage = null;
        try {
            BasePage = clazz.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BasePage jsClickElement(WebElement element) {
        logger.info(": click element using js executor");
        jsExecutor.executeScript("arguments[0].click();", element);
        return this;
    }
}