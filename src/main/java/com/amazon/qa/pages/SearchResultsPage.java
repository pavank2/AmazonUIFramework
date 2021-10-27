package com.amazon.qa.pages;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchResultsPage extends BaseTest {

    @FindBy(css = "li[aria-label='Free Shipping by Amazon']")
    private List<WebElement> freeShipping;

    @FindBy (css = "li[aria-label='Mars']")
    private WebElement marsCheckBox;

    @FindBy (css = "li[aria-label='Bounty']")
    private WebElement bountyCheckBox;

    @FindBy(id="s-result-sort-select")
    private WebElement sortBy;

    @FindBy(id="newAccordionRow")
    private List<WebElement> oneTimeBuy;

    @FindBy(id="add-to-cart-button")
    private WebElement addToCart;


    public SearchResultsPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public float selectCheapestChocolate(String choc){
       if (choc == "Mars" && freeShipping.size() > 0)
         freeShipping.get(0).click();
       if (choc == "Mars")
            marsCheckBox.click();

        Select s = new Select(sortBy);
        s.selectByIndex(1);
        List<WebElement> chocolatesList = driver.findElements(By.cssSelector("div[class='a-section a-spacing-medium']"));

        System.out.println(chocolatesList.size());
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
                System.out.println("minPrice: "+minPrice);
                TestUtil.sleepForNSeconds(2);
                chocolate.click();
                break;
            }
        }

        TestUtil.checkElementExistsAndClick(oneTimeBuy);
        WebDriverWait w = new WebDriverWait(driver,10);
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
        addToCart.click();
        return minPrice;
    }

    //Checks if the element exists
    private boolean checkElementExists(WebElement element, By locator){
        System.out.println("Checking each condition");
        return element.findElements(locator).size() > 0;
    }

}
