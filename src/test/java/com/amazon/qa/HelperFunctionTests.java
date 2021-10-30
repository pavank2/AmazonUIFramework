package com.amazon.qa;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.pages.LandingPage;
import com.amazon.qa.pages.SearchResultsPage;
import com.amazon.qa.pages.ShoppingCartPage;
import com.amazon.qa.pages.SignInPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Logger;

/**
 * Unit tests for helper functions from all the Page Object classes
 * @Author: PK
 */

public class HelperFunctionTests extends BaseTest {

    static Logger log = Logger.getLogger(HelperFunctionTests.class.getName());
    BaseTest base = new BaseTest();

    LandingPage landingPage;
    SignInPage signInPage;
    ShoppingCartPage shoppingCart;
    SearchResultsPage searchResults;
    WebDriver driver;

    @BeforeClass
    public void setup() throws Throwable {
        driver = base.initialize();
        landingPage = new LandingPage(driver);
        signInPage = new SignInPage(driver);
        shoppingCart = new ShoppingCartPage(driver);
        searchResults = new SearchResultsPage(driver);

    }

    @BeforeMethod
    public void user_navigates_to_website() {
        log.info("Navigating to website");
        landingPage.navigateToURL("https://amazon.com");
    }

    /**
     * User sign in with wrong credentials
     */
    @Test (expectedExceptions = {IllegalStateException.class})
    public void test_sign_in_wrong_cred(){
     landingPage.userSignIn("wrongmail@gmail.com","wrongPassword");

    }

    /**
     * User searches for random entry instead of Mars or Bounty
     */
    @Test
    public void test_random_search_entry(){
        Assert.assertEquals(false,landingPage.searchForItem("Random"));
    }

    /**
     * Check if user signed in successfully
     */
    @Test
    public void user_signed_in_successfully(){
        landingPage.userSignIn("getsugarasengan@gmail.com","temp1234");
        Assert.assertTrue(landingPage.checkUserSignedIn());
    }

    /**
     * User provides an invalid zipcode
     */
    @Test (expectedExceptions = {IllegalStateException.class})
    public void test_select_location_wrong_zipcode(){
        LandingPage page = new LandingPage(driver);

      landingPage.selectLocationWrongZip("1234");
    }

    @AfterClass
    public void teardown(){
        driver.quit();
    }

}
