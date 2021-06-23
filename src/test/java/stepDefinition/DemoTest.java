package stepDefinition;

import assertion.LoginPageAssertion;
import assertion.partsAndSupplies.CompressorsPageAssertion;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.LoginPage;
import page.partsAndSupplies.CompressorsPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoTest extends AbstractTest {

    @Given("Login to lennoxPros")
    public void login_to_lennoxPros() {
        super.setUp();
        loginPage = new LoginPage(driver);
        loginPage.startAssertions(LoginPageAssertion.class)
                .assertIfUserAtLennoxHomepage()
                .endAssertion()
                .clickOnLoginLinkBtn()
                .inputUserID(configuration.getUserName())
                .inputPassword(configuration.getPassword())
                .clickOnSignInBtn()
        ;
    }

    @Then("User able to view home page")
    public void user_able_to_view_home_page() {
        loginPage.startAssertions(LoginPageAssertion.class)
                .assertIfUserAtLennoxHomepage()
                .endAssertion();
    }

    @When("Navigate via Menu to Parts And Supplies to Compressors")
    public void navigate_via_Menu_to_Parts_And_Supplies_to_Compressors(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheet = dataTable.asList();
        compressorsPage = new CompressorsPage(driver);
        compressorsPage.clickOnNavigationMenuContainer()
                .SelectPrimaryCatalog(excel.readExcel(sheet.get(0), 3, 0))
                .SelectSubCatalog(excel.readExcel(sheet.get(1), 1, 0))
        ;
    }

    @Then("Validate user at the compressor page")
    public void validate_user_at_the_compressor_page(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheetName = dataTable.asList();
        List<String> subCatalogDescription = Arrays.asList(excel.readExcel(sheetName.get(0), 3, 3), excel.readExcel(sheetName.get(1), 4, 3));
        compressorsPage.startAssertions(CompressorsPageAssertion.class)
                .assertAtCompressorPage(subCatalogDescription)
                .endAssertion()
        ;
    }

    @When("Select Air Compressors")
    public void select_Air_Compressors(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheetName = dataTable.asList();
        compressorsPage.clickOnAirCompressors(excel.readExcel(sheetName.get(0), 1, 0), 3)
        ;
    }

    @Then("Validate the landing page description")
    public void validate_the_landing_page_description(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheetName = dataTable.asList();
        List<String> subCatalogDescription = Arrays.asList(excel.readExcel(sheetName.get(0), 3, 3), excel.readExcel(sheetName.get(1), 5, 3));
        compressorsPage.startAssertions(CompressorsPageAssertion.class)
                .assertAtCompressorPage(subCatalogDescription)
                .endAssertion()
        ;
    }

    @When("Locate for the product 10T46 on the page, if not found navigate to the subsequent pages until the product is found. If the product is not listed in any of the pages then provide the details in the report.")
    public void locate_for_the_product_10T46_on_the_page_if_not_found_navigate_to_the_subsequent_pages_until_the_product_is_found_If_the_product_is_not_listed_in_any_of_the_pages_then_provide_the_details_in_the_report(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheetName = dataTable.asList();
        compressorsPage.locateTheCatalogNumber(excel.readExcel(sheetName.get(0), 1, 0));
    }

    @Then("Collect all the listed details pertaining to the product and store")
    public void collect_all_the_listed_details_pertaining_to_the_product_and_store(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheetName = dataTable.asList();
        compressorsPage.getProductDetails(excel.readExcel(sheetName.get(0), 1, 0));
    }

    @When("Click on the Product and it will lead to Product detail page.")
    public void click_on_the_Product_and_it_will_lead_to_Product_detail_page(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheetName = dataTable.asList();
        compressorsPage.clickOnCompressor(excel.readExcel(sheetName.get(0), 1, 0));
    }

    @Then("Collect all the product details which are highlighted and compare it with the details from the previous page.")
    public void collect_all_the_product_details_which_are_highlighted_and_compare_it_with_the_details_from_the_previous_page(io.cucumber.datatable.DataTable dataTable) throws IOException {
        List<String> sheetName = dataTable.asList();
        List<String> productDetails = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            productDetails.add(excel.readExcel(sheetName.get(0), 1, i));
        }
        compressorsPage.startAssertions(CompressorsPageAssertion.class)
                .assertIfProductDetailsShowsCorrectly(productDetails)
                .endAssertion()
        ;
    }

    @After
    public void close() {
        super.close();
    }
}