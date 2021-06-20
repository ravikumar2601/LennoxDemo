package page.partsAndSupplies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.BasePage;
import utilsBrowser.Excel.Excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompressorsPage extends BasePage {

    private static final String PRIMARY_CATALOG = "(//a[contains(text(),'%s')])[3]";
    private static final String SUB_CATALOG = "(//a[contains(text(),'%s')])[%d]";
    private static final String SUB_CATALOG_SIZE = "//a[contains(text(),'%s')]";
    private static final String CATALOG_NUMBER = "(//div[@class='sku'])[%d]";
    private static final String PAGE_NUMBERS = "(//strong[@class='dataCount'])[%d]";
    private static final String TITLE = "(//h2[@class='title'])[%s]";
    private static final String MODEL_NO = "(//div[@class='sku'])[%s]";
    private static final String YOUR_PRICE = "(//p[@class='your-price'])[%s]";
    private static final String LIST_PRICE = "(//p[@class='list-price'])[%s]";
    private static final String STANDARD_AVAILABILITY_STATUS = "(//div[@class='ship-to-availability availability'])[%s]";
    private static final String PICKUP_IN_STORE_AVAILABILITY = "(//label[contains(@for,'pickup-method')])[%s]";
    private static final String ZIP_CODE = "(//p//span[@class='zip-replace'])[%s]";
    private static final String ADD_TO_CART = "(//span[contains(text(),'Add To Cart')])[%s]";
    private static final String COMPRESSOR_IMG = "(//div[@class='thumb']//img[contains(@src,'https://images.lennoxpros.com/is/image/LennoxIntl')])[%s]";


    @FindBy(xpath = "//a[contains(@href,'navigation')]")
    private WebElement navigation;
    @FindBy(xpath = "//div[@class='col-md-6']//h1")
    private WebElement compressorText;
    @FindBy(xpath = "//div[@class='description']//p")
    private WebElement description;
    @FindBy(xpath = "//div[@class='sku']/span[1]")
    private List<WebElement> catNo;
    @FindBy(xpath = "//strong[@class='dataCount']")
    private List<WebElement> pageNos;
    @FindBy(xpath = "//h1[@itemprop='name']")
    private WebElement productName;
    @FindBy(xpath = "//span[@class='ff-regular availability-info-ot']")
    private WebElement standardShipStatus;
    @FindBy(xpath = "//span[@class='pdp-model-number']")
    private WebElement modelNumber;
    @FindBy(xpath = "//strong[contains(text(),'Your Price')]//span")
    private WebElement price1;
    @FindBy(xpath = "//div[1]/p[@class='price']")
    private WebElement price2;
    @FindBy(xpath = "//label[@for='dm-pickup']")
    private WebElement pickUpInStoreStatus;
    @FindBy(xpath = "(//span[contains(text(),'ADD TO CART')])[2]")
    private WebElement addToCartBtnStatus;

    Excel excel = new Excel();

    public CompressorsPage(WebDriver driver) {
        super(driver);
    }

    public CompressorsPage clickOnNavigationMenuContainer() {
        fluentWait(navigation);
        moveToElementAndForceClick(navigation);
        return this;
    }

    public CompressorsPage SelectPrimaryCatalog(String catalog) {
        WebElement primaryCatalog = driver.findElement(By.xpath(String.format(PRIMARY_CATALOG, catalog)));
        fluentWait(primaryCatalog);
        moveToElementAndForceClick(primaryCatalog);
        return this;
    }

    public CompressorsPage SelectSubCatalog(String subCatalog) {
        WebElement catalog = driver.findElement(By.xpath(String.format(SUB_CATALOG, subCatalog, getSubCatalogSize(subCatalog))));
        fluentWait(catalog);
        moveToElementAndForceClick(catalog);
        return this;
    }

    public int getSubCatalogSize(String subCatalog) {
        List<WebElement> subCatalogSize = driver.findElements(By.xpath(String.format(SUB_CATALOG_SIZE, subCatalog)));
        return subCatalogSize.size();
    }

    public List<String> getDescription() {
        String catalog = compressorText.getText();
        String catalogDescription = description.getText();
        return Arrays.asList(catalog, catalogDescription);
    }

    public CompressorsPage clickOnAirCompressors(String subCatalog, int index) {
        scrollToTheBottom();
        WebElement catalog = driver.findElement(By.xpath(String.format(SUB_CATALOG, subCatalog, index)));
        fluentWait(catalog);
        catalog.click();
        return this;
    }

    public int getCatalogNumber(String catalogNo) throws IOException {

        List<String> catNos = new ArrayList<>();
        int currentPage = 0;
        int productPosition = 0;
        for (int i = 1; i <= pageNos.size(); i++) {
            if (i > 1) {
                if (currentPage == i - 1) {
                    break;
                }
                waitInSec(2);
                scrollToTheBottom();
                scrollToTheBottom();
                scrollToTheBottom();
                waitInSec(2);
                WebElement pageNo = driver.findElement(By.xpath(String.format(PAGE_NUMBERS, i)));
                scrollToTheBottom();
                jsClickElement(pageNo);
            }
            for (int j = 1; j <= catNo.size(); j++) {
                WebElement number = driver.findElement(By.xpath(String.format(CATALOG_NUMBER, j)));
                String Cat = number.getText().substring(7, 12).trim();
                System.out.println("number" + j + " :" + Cat);
                if (Cat.equals(catalogNo)) {
                    currentPage = i;
                    productPosition = j;
                    System.out.println("Catalog located page No: " + currentPage);
                    String position = String.valueOf(j);
                    excel.writeExcel("ProductListItem", position, 0);
                    break;
                } else if (i > 2) {
                    if (i == pageNos.size()) {
                        if (j == catNo.size()) {
                            System.out.println(catalogNo + "--->" + "Product not found in pages...............");
                        }
                    }
                }
            }
        }
        return productPosition;
    }

    public CompressorsPage getProductDetails(String index) throws IOException {

        String status = "";

        scrollToTheBottom();

        WebElement productTitle = driver.findElement(By.xpath(String.format(TITLE, index)));
        String productTitleText = productTitle.getText();

        WebElement modelNo = driver.findElement(By.xpath(String.format(MODEL_NO, index)));
        String modelNoText = modelNo.getText().substring(26, 37).trim();
        System.out.println("modelText ----" + modelNoText);

        waitInSec(2);
        WebElement yourPrice = driver.findElement(By.xpath(String.format(YOUR_PRICE, index)));
        fluentWait(yourPrice);
        String yourPriceText = yourPrice.getText().replace(",", "").trim();

        WebElement listPrice = driver.findElement(By.xpath(String.format(LIST_PRICE, index)));
        fluentWait(listPrice);
        String listPriceText = listPrice.getText().substring(11, 20).replace(",", "").trim();

        waitInSec(2);
        WebElement standardAvailabilityStatus = driver.findElement(By.xpath(String.format(STANDARD_AVAILABILITY_STATUS, index)));
        fluentWait(standardAvailabilityStatus);
        String standardAvailabilityStatusText = standardAvailabilityStatus.getText().trim();

        WebElement pickUpInStoreStatus = driver.findElement(By.xpath(String.format(PICKUP_IN_STORE_AVAILABILITY, index)));
        fluentWait(pickUpInStoreStatus);
        String pickUpInStoreStatusText = pickUpInStoreStatus.getText().trim();

        WebElement zipCode = driver.findElement(By.xpath(String.format(ZIP_CODE, index)));
        fluentWait(zipCode);
        String zipCodeText = zipCode.getText().trim();

        WebElement addToCart = driver.findElement(By.xpath(String.format(ADD_TO_CART, index)));

        if (addToCart.isEnabled() == true) {
            status = "Enabled";
        } else if (addToCart.isEnabled() == false) {
            status = "Disabled";
        }

        List<String> details = Arrays.asList(productTitleText, modelNoText, yourPriceText, listPriceText, standardAvailabilityStatusText, pickUpInStoreStatusText, zipCodeText, status);
        System.out.println("Details  :" + details);
        excel.writeMultipleValuesInExcel("Product detail", details);
        return this;
    }

    public CompressorsPage clickOnCompressor(String index) {
        WebElement compressor = driver.findElement(By.xpath(String.format(COMPRESSOR_IMG, index)));
        fluentWait(compressor);
        moveToElementAndForceClick(compressor);
        return this;
    }

    public List<String> getProductDetailsPageValues() {
        String status = "";

        scrollToTheBottom();

        String productTitle = productName.getText();
        fluentWait(modelNumber);
        String modelNo = modelNumber.getText();
        String yourPrice = price1.getText();
        fluentWait(price2);
        System.out.println("valuessss...." + price2.getText());
        String listPrice = price2.getText().substring(33, 41).trim();
        String standardShipAvailabilityStatus = standardShipStatus.getText();
        String pickUpInStoreAvailabilityStatus = pickUpInStoreStatus.getText();

        WebElement zipCode = driver.findElement(By.xpath(String.format(ZIP_CODE, 1)));
        String zipCodeText = zipCode.getText().trim();


        if (addToCartBtnStatus.isEnabled() == true) {
            status = "Enabled";
        } else if (addToCartBtnStatus.isEnabled() == false) {
            status = "Disabled";
        }

        List<String> actualDetails = Arrays.asList(productTitle, modelNo, yourPrice, listPrice, standardShipAvailabilityStatus, pickUpInStoreAvailabilityStatus, zipCodeText, status);
        return actualDetails;
    }
}
