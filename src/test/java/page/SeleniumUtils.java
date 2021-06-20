package page;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public interface SeleniumUtils {

    String getPageUrl();

    void waitUntil(ExpectedCondition<?> expectedCondition, int timeoutSeconds);

    void waitUntil(ExpectedCondition<?> expectedCondition);

    WebElement fluentWait(final WebElement element);

    void waitInSec(Integer sec);

    void doubleClick(WebElement element);

    void scrollToTheBottom();

    void scrollToTheTop();

    void moveToElementAndForceClick(WebElement element);

    <T extends BasePage> T goToPreviousPage(Class<T> clazz) throws RuntimeException;

    BasePage switchToBrowserTab(int windowNumber);

    void switchToWindow(String handle);

    void refresh();

    BasePage switchToTab(int windowNumber);

    boolean isAt(String pageUrl);

    <T extends BasePage> void acceptAlertIfPresent(Class<T> clazz) throws RuntimeException;

    String getBaseURL();

    <T extends BasePage> void dismissAlertIfPresent(Class<T> clazz) throws RuntimeException;

    BasePage jsClickElement(WebElement element);
}
