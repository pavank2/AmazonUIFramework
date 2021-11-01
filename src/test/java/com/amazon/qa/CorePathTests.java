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

import java.text.DecimalFormat;
import java.util.logging.Logger;
/**
 * Two E2E Tests for the two core paths for the Checkout functionality
 * Test 1: User signs in, searches for two products, selects the cheapest available ones, adds them to cart and verifies the amount
 * Test 2: Searches for products first, selects a single product, adds them to cart and verifies user sign in during checkout
 * @Author: PK
 *
 */
public class CorePathTests extends BaseTest {
    BaseTest base = new BaseTest();
    WebDriver driver;
    LandingPage landingPage;
    SignInPage signInPage;
    ShoppingCartPage shoppingCart;
    SearchResultsPage searchResults;
    static Logger log = Logger.getLogger(CorePathTests.class.getName());

    @BeforeClass
    public void setup() throws Throwable {
        driver = base.initialize();
        landingPage = new LandingPage(driver);
        signInPage = new SignInPage(driver);
        shoppingCart = new ShoppingCartPage(driver);
        searchResults = new SearchResultsPage(driver);
        log.info("Initial setup completed");

    }

    @BeforeMethod
    public void user_navigates_to_website() {
        landingPage.navigateToURL("http://amazon.com");
   }


   @AfterClass
   public void teardown(){
        driver.quit();
   }
   @Test (priority=0)
   public void test_signin_search_checkout(){
       System.out.println("***TEST 001***");
       landingPage.userSignIn("getsugarasengan@gmail.com","temp1234");
       landingPage.selectLocation("56002");
       float initialCartPrice = shoppingCart.getTotalPrice();
       System.out.println("Initial cart price: "+initialCartPrice);
       if (landingPage.searchForItem("Mars chocolate")) {
           float marsPrice = searchResults.selectCheapestChocolate("Mars");
           System.out.println("Mars price: " + marsPrice);
           if (landingPage.searchForItem("Bounty chocolate")) {
               float bountyPrice = searchResults.selectCheapestChocolate("Bounty");
               System.out.println("Bounty price: " + bountyPrice);

               float actualTotalPrice = marsPrice + bountyPrice;

               System.out.println("Actual total price: " + actualTotalPrice);
               float finalCartPrice = shoppingCart.getTotalPrice();
               float expectedTotalPrice = finalCartPrice - initialCartPrice;
               String roundedExpectedPrice = roundFloatValues(expectedTotalPrice);
               System.out.println("Rounded expected price " + roundedExpectedPrice);
               String roundedActualPrice = roundFloatValues(actualTotalPrice);
               System.out.println("Rounded actual price " + roundedActualPrice);
               Assert.assertEquals(roundedExpectedPrice, roundedActualPrice);
            } else
               Assert.fail("Bounty chocolate not entered");
        } else
            Assert.fail("Mars chocolate not entered");
       landingPage.userSignOut();
   }


    @Test  (priority=1)
    public void test_search_checkout_signin(){
        System.out.println("***TEST 002***");
        landingPage.selectLocation("56002");
        landingPage.searchForItem("Mars chocolate");
        float marsPrice = searchResults.selectCheapestChocolate("Mars");
        shoppingCart.proceedToCheckOut();
        signInPage.enterEmailAndPassword("getsugarasengan@gmail.com","temp1234");
    }

    private String roundFloatValues(float num){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(num);
    }
}
