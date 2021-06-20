package assertion;

import page.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPageAssertion extends AbstractAssertion<LoginPage> {

    public LoginPageAssertion assertIfUserAtLennoxHomepage() {
        assertThat(basePage.isLennoxLogoDisplayed()).as("Verify that user able to view the LennoxPro logo").isTrue();
        return this;
    }
}
