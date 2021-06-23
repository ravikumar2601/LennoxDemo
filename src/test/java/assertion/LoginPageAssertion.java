package assertion;

import page.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPageAssertion extends AbstractAssertion<LoginPage> {

    public LoginPageAssertion assertIfUserAtLennoxHomepage() {
        assertThat(basePage.isMostPopularProductsTextDisplayed()).as("Verify that user able to view the Most Popular Products Text ").isTrue();
        return this;
    }
}
