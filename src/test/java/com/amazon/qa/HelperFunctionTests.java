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
        landingPage.navigateToURL("https://amazon.com");
    }

    /**
     * User provides an invalid zipcode
     */
    @Test (expectedExceptions = {IllegalStateException.class},priority=2)
    public void test_select_location_wrong_zipcode(){
        System.out.println("***TEST 003***");

        LandingPage page = new LandingPage(driver);
        landingPage.selectLocationWrongZip("1234");
    }

    /**
     * User sign in with wrong credentials
     */
    @Test (expectedExceptions = {IllegalStateException.class}, priority=3)
    public void test_sign_in_wrong_cred(){
        System.out.println("***TEST 004***");
        landingPage.userSignIn("wrongmail@gmail.com","wrongPassword");
    }

    /**
     * User searches for random entry instead of Mars or Bounty
     */
    @Test (priority=4)
    public void test_random_search_entry(){
        System.out.println("***TEST 005***");
        Assert.assertEquals(false,landingPage.searchForItem("Random"));
    }

    /**
     * Check if user signed in successfully
     */
    @Test (priority=5)
    public void user_signed_in_successfully(){
        System.out.println("***TEST 006***");

        landingPage.userSignIn("getsugarasengan@gmail.com","temp1234");
        Assert.assertTrue(landingPage.checkUserSignedIn());
    }

    @AfterClass
    public void teardown(){
        driver.quit();
    }

}
