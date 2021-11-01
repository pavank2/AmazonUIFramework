package com.amazon.qa.pages;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Page Objects and methods for ShoppingCartPage
 * @Author: PK
 */
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
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public float getTotalPrice(){
       if (TestUtil.checkElementExists(emptyCartMsg)){ //Check if cart is empty
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