package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "j_username")
    private WebElement jUserName;
    @FindBy(id = "j_password")
    private WebElement jPassword;
    @FindBy(id = "loginSubmit")
    private WebElement signInBtn;
    @FindBy(xpath = "//img[@alt='LennoxPros Logo']")
    private WebElement lennoxProsLogo;
    @FindBy(xpath = "//a[contains(text(),'Sign In')]")
    private WebElement loginLinkbtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage inputUserID(String userName) {
        fluentWait(jUserName);
        jUserName.clear();
        jUserName.sendKeys(userName);
        return this;
    }

    public LoginPage inputPassword(String passWord) {
        fluentWait(jPassword);
        jPassword.clear();
        jPassword.sendKeys(passWord);
        return this;
    }

    public LoginPage clickOnSingInBtn() {
        scrollToTheBottom();
        fluentWait(signInBtn);
        signInBtn.click();
        return this;
    }

    public boolean isLennoxLogoDisplayed() {
        fluentWait(lennoxProsLogo);
        return lennoxProsLogo.isDisplayed();
    }

    public LoginPage clickOnLoginLinkBtn() {
        fluentWait(loginLinkbtn);
        loginLinkbtn.click();
        return this;
    }
}
