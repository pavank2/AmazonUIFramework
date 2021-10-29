package com.amazon.qa.pages;

import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.qa.base.BaseTest;

import java.util.List;

public class LandingPage extends BaseTest {
    WebDriver driver;
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

    @FindBy(id="GLUXZipUpdateInput")
    private WebElement zipCodeField;

    @FindBy(css="div[role='button']")
    private WebElement zipApplyButton;

    @FindBy (name="glowDoneButton")
    private WebElement doneButton;

    @FindBy(id="nav-link-accountList")
    private List<WebElement> signInMsg;

    @FindBy(id="nav-item-signout")
    private WebElement signOut;

    @FindBy(id="nav-link-accountList-nav-line-1")
    private WebElement signInConfirmation;

    @FindBy(id="GLUXZipError")
    private List<WebElement> wrongZipCode;

    @FindBy(id="GLUXChangePostalCodeLink")
    private List<WebElement> changeZipCode;


    public LandingPage(WebDriver driver) {
     //   super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectLocation(String zipCode){

     //If delivery location is not already selected
      if(!existingDeliveryLocation.getText().contains(zipCode)){
          deliveryLocation.click();
          if(TestUtil.checkElementExists(changeZipCode)) {
              changeZipCode.get(0).click();
              zipCodeField.clear();
          }
          zipCodeField.sendKeys(zipCode);
          TestUtil.sleepForNSeconds(2);
          zipApplyButton.click();
          if (TestUtil.checkElementExists(wrongZipCode)
                  && wrongZipCode.get(0).getAttribute("innerText").equals("Please enter a valid US zip code")) {
              throw new IllegalStateException("Wrong zip code entered");
          }
          doneButton.click();
      }

    }

    public void userSignIn(String username,String password){
        SignInPage signInPage  = new SignInPage(driver);
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
//         if (TestUtil.checkElementExists(signInMsg))
//             System.out.println("Signout successful");
//         else
//             System.out.println("Signout unsuccessful. May impact next tests");

     }

     public boolean checkUserSignedIn(){
        return signInConfirmation.getAttribute("innerText").contains("Hello,");
     }

}
