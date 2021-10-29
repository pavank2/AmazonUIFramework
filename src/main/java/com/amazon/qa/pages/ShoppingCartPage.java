package com.amazon.qa.pages;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.util.TestUtil;
import com.aventstack.extentreports.model.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ShoppingCartPage extends BaseTest {
 WebDriver driver;
    @FindBy(css="span[id='sc-subtotal-amount-buybox'] span[class*='a-size-medium']")
    private WebElement totalPrice;

    @FindBy(id="nav-cart-count-container")
    private WebElement cart;

    @FindBy(css= "a[aria-label='0 items in cart']")
    private List<WebElement> emptyCartMsg;

    @FindBy(name="proceedToRetailCheckout")
    private WebElement checkoutButton;


    public ShoppingCartPage(WebDriver driver) {
    //    super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public float getTotalPrice(){
       if (TestUtil.checkElementExists(emptyCartMsg)){ //Check is cart not empty
           return 0;
        } else {
           cart.click();
           String priceString= totalPrice.getAttribute("innerText").substring(1);
           return Float.parseFloat(priceString);
       }
    }

    public void proceedToCheckOut(){
        cart.click();
        checkoutButton.click();
    }
}