package com.amazon.qa;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.pages.LandingPage;
import com.amazon.qa.pages.SearchResultsPage;
import com.amazon.qa.pages.ShoppingCartPage;
import com.amazon.qa.pages.SignInPage;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CorePathTests extends BaseTest {
    BaseTest base = new BaseTest();

    LandingPage landingPage;
    SignInPage signInPage;
    ShoppingCartPage shoppingCart;
    SearchResultsPage searchResults;
    @BeforeClass
    public void setup() throws Throwable {
        base.initialize();
        landingPage = new LandingPage();
        signInPage = new SignInPage();
        shoppingCart = new ShoppingCartPage();
        searchResults = new SearchResultsPage();

    }

    @BeforeMethod
    public void user_navigates_to_website() {
        landingPage.navigateToURL("https://amazon.com");
   }

   @Test (enabled = false)
   public void test_signin_search_checkout(){
       landingPage.userSignIn("getsugarasengan@gmail.com","temp1234");
       landingPage.selectLocation("56002");
        float initialCartPrice = shoppingCart.getTotalPrice();
        System.out.println(initialCartPrice);
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
            Assert.fail("Bounty chocolate not entered");
   }


    @Test
    public void test_search_checkout_signin(){
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
