package com.amazon.qa.pages;

import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.qa.base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LandingPage extends BaseTest {
    @FindBy(id="nav-link-accountList-nav-line-1")
    private WebElement signInButton;



    @FindBy(id="twotabsearchtextbox")
    private WebElement searchBar;

    @FindBy(id="nav-search-submit-button")
    private WebElement searchButton;



    @FindBy(id="nav-global-location-popover-link")
    private WebElement deliveryLocation;

    @FindBy(id="glow-ingress-line2")
    private WebElement existingDeliveryLocation;

    @FindBy(xpath="//input[@data-action='GLUXPostalInputAction']")
    private WebElement pinCode;

    @FindBy(css="div[role='button']")
    private WebElement zipApplyButton;

    @FindBy (css="div[class='a-popover-footer'] input[id='GLUXConfirmClose']")
    private WebElement confirmClose;

    @FindBy(css="a[data-nav-role='signin']")
    private List<WebElement> signInMsg;

    @FindBy(id="nav-item-signout")
    private WebElement signOut;

    @FindBy(id="nav-link-accountList-nav-line-1")
    private WebElement signInConfirmation;

    @FindBy(id="GLUXZipError")
    private List<WebElement> wrongZipCode;

    public LandingPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void selectLocation(String zipCode){

     //If delivery location is not already selected
      if(!existingDeliveryLocation.getText().contains(zipCode)){
          deliveryLocation.click();
          pinCode.sendKeys(zipCode);
          zipApplyButton.click();
          if (TestUtil.checkElementExists(wrongZipCode)
                  && wrongZipCode.get(0).getAttribute("innerText").equals("Please enter a valid US zip code")) {
              throw new IllegalStateException("Wrong zip code entered");
          }
          confirmClose.click();
      }

    }

    public void userSignIn(String username,String password){
        SignInPage signInPage  = new SignInPage();
        clickSignIn();
        signInPage.enterEmailAndPassword(username,password);
    }

    public void navigateToURL(String url) {
        driver.get(url);
    }

    public void clickSignIn() {

        signInButton.click();
    }

    public boolean searchForItem(String item){
       if (item.contains("Mars") || item.contains("Bounty")) {
           TestUtil.sleepForNSeconds(3);
           searchBar.sendKeys(item);
           searchButton.click();
           return true;
       } else
           return false;

    }

    public String getSignInMsg(){
        if (TestUtil.checkElementExists(signInMsg)){
            return signInMsg.get(0).getAttribute("innerText");
        } else
            return null;
    }

     public void userSignOut(){
         Actions action  =new Actions(driver);

         action.moveToElement(signInMsg.get(0)).build().perform();
         signOut.click();
     }

     public boolean checkUserSignedIn(){
        return signInConfirmation.getAttribute("innerText").contains("Hello,");
     }

}
