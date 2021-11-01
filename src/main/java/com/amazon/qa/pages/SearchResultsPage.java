package com.amazon.qa.pages;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Page objects and methods for SearchResultsPage
 * @Author: PK
 */
public class SearchResultsPage extends BaseTest {
    WebDriver driver;
    @FindBy(css = "li[aria-label='Free Shipping by Amazon']")
    private List<WebElement> freeShipping;

    @FindBy (css = "li[aria-label='Mars']")
    private WebElement marsCheckBox;

    @FindBy (css = "li[aria-label='Bounty']")
    private WebElement bountyCheckBox;

    @FindBy(xpath="//span[@aria-label='Sort by:']//span[@data-action='a-dropdown-button']")
    private WebElement sortBy;

    @FindBy(id="newAccordionRow")
    private List<WebElement> oneTimeBuy;

    @FindBy(id="add-to-cart-button")
    private WebElement addToCart;

    @FindBy(xpath="//span[contains(text(),'Added to Cart')]")
    private List<WebElement> cartAddSuccess;

    @FindBy(xpath="//h1[contains(text(),'Added to Cart')]")
    private List<WebElement> cartAddSuccess2;


    public SearchResultsPage(WebDriver driver ) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Seelcts the cheapest chocolate which is in stock and adds to cart
     * @param choc
     * @return
     */
    public float selectCheapestChocolate(String choc){
       if (choc == "Mars" && TestUtil.checkElementExists(freeShipping))
         freeShipping.get(0).click();
         selectFilterType("Price: Low to High");
        List<WebElement> chocolatesList = driver.findElements(By.cssSelector("div[class='a-section a-spacing-medium']"));
        float minPrice= 0;
        for (WebElement chocolate:chocolatesList){
            System.out.println("Looking for cheapest "+choc+" chocolate in stock. Please be patient...");
            By notInStock = By.xpath(".//span[@aria-label='In stock soon.']");
            By onlyOneInStock = By.xpath(".//span[@aria-label='Only 1 left in stock - order soon.']");
            By priceLocator = By.xpath(".//span[@class='a-price']//span[@class='a-offscreen']");
            if (!checkElementExists(chocolate,notInStock) && !checkElementExists(chocolate,onlyOneInStock)
                    && checkElementExists(chocolate,priceLocator)) {
                WebElement priceElement = chocolate.findElement(priceLocator);
                minPrice = Float.parseFloat(priceElement.getAttribute("innerText").substring(1));
                TestUtil.sleepForNSeconds(2);
                chocolate.click();
                break;
            }
        }
        TestUtil.checkElementExistsAndClick(oneTimeBuy);
        WebDriverWait w = new WebDriverWait(driver,10);
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
        addToCart.click();
        if (!TestUtil.checkElementExists(cartAddSuccess) && !TestUtil.checkElementExists(cartAddSuccess2)) {
            System.out.println("ALERT!"+choc+" not added to cart");
            minPrice = 0;
        }
        return minPrice;
    }

    //Checks if the element exists
    private boolean checkElementExists(WebElement element, By locator){
        System.out.println("Checking each condition");
        return element.findElements(locator).size() > 0;
    }

    public void selectFilterType(String option) {
        // Open the dropdown so the options are visible
        TestUtil.sleepForNSeconds(4);
        sortBy.click();
        TestUtil.sleepForNSeconds(2);
        // Get all of the options
        List<WebElement> options = driver.findElements(By.xpath("//ul[@class='a-nostyle a-list-link']/li"));
        // Loop through the options and select the one that matches
        for (WebElement opt : options) {
            if (opt.getText().equals(option)) {
                opt.click();
                return;
            }
        }
        throw new NoSuchElementException(option + " not found in dropdown");
    }

}
