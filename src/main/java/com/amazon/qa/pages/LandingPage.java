package com.amazon.qa.pages;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 *  Page objects and methods for Main page for Amazon website which has the search bar,sign in option etc.
 * @Author: PK
 */
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

    @FindBy(css="input[class='a-button-input'][type='submit'][aria-labelledby='GLUXZipUpdate-announce']")
    private WebElement zipApplyButton;

    @FindBy (name="glowDoneButton")
    private List<WebElement> doneButton;

    @FindBy(id="nav-link-accountList")
    private WebElement accountLabel;

    @FindBy(xpath="//span[contains(text(),'Hello')]")
    private WebElement signedInMsg;


    @FindBy(id="nav-item-signout")
    private WebElement signOut;

    @FindBy(id="nav-link-accountList-nav-line-1")
    private WebElement signInConfirmation;

    @FindBy(id="GLUXZipError")
    private List<WebElement> wrongZipCode;

    @FindBy(id="GLUXChangePostalCodeLink")
    private List<WebElement> changeZipCode;

    @FindBy(xpath="//div[@class='a-popover-footer']//input[@id='GLUXConfirmClose']")
    private List<WebElement> continueButton;


    public LandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Select location specifying zipcode as Input
     * @param zipCode
     */
    public void selectLocation(String zipCode){
     //If delivery location is not as expected
      if(!existingDeliveryLocation.getText().contains(zipCode)){
          TestUtil.sleepForNSeconds(3);
          deliveryLocation.click();
          TestUtil.sleepForNSeconds(3);
          if(TestUtil.checkElementExists(changeZipCode)) {
            changeZipCode.get(0).click();
              zipCodeField.clear();
          }
          zipCodeField.sendKeys(zipCode);
          zipApplyButton.click();
          TestUtil.sleepForNSeconds(3);
          if (TestUtil.checkElementExists(doneButton))
            doneButton.get(0).click();
          else if (TestUtil.checkElementExists(continueButton))
            continueButton.get(0).click();
      }

    }

    /**
     * Selection location for negative testcase with wrong zip code
     * @param zipCode
     */
    public void selectLocationWrongZip(String zipCode){
        deliveryLocation.click();
        TestUtil.waitForElement(By.id("GLUXZipUpdateInput"));
        zipCodeField.sendKeys(zipCode);
        zipApplyButton.click();
        TestUtil.sleepForNSeconds(2);
        if (TestUtil.checkElementExists(wrongZipCode)){
            throw new IllegalStateException("Wrong zip code entered");
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

    /**
     * Searches for Mars or Bounty chocolate
     * @param item Takes chocolate as param
     * @return Returns true if correct chocolate is entered
     */
    public boolean searchForItem(String item){
       if (item.contains("Mars") || item.contains("Bounty")) {
          TestUtil.sleepForNSeconds(5);
           searchBar.sendKeys(item);
           searchButton.click();
           return true;
       } else
           return false;

    }

//    public String getSignInMsg(){
//        if (TestUtil.checkElementExists(signInMsg)){
//            return signInMsg.get(0).getAttribute("innerText");
//        } else
//            return null;
//    }

     public void userSignOut(){
         Actions action  = new Actions(driver);
         TestUtil.sleepForNSeconds(2);
         action.moveToElement(accountLabel).build().perform();
         TestUtil.sleepForNSeconds(2);
         signOut.click();

     }

     public boolean checkUserSignedIn(){
        return signInConfirmation.getAttribute("innerText").contains("Hello,");
     }

}
