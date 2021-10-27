package com.amazon.qa;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.pages.LandingPage;
import com.amazon.qa.pages.SearchResultsPage;
import com.amazon.qa.pages.ShoppingCartPage;
import com.amazon.qa.pages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HelperFunctionTests extends BaseTest {
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

    @Test (expectedExceptions = {IllegalStateException.class}, enabled = false)
    public void test_sign_in_wrong_cred(){
     landingPage.userSignIn("wrongmail@gmail.com","wrongPassword");

    }

    @Test
    public void test_random_search_entry(){
        Assert.assertEquals(false,landingPage.searchForItem("Random"));
    }

    @Test
    public void user_signed_in_successfully(){
        landingPage.userSignIn("getsugarasengan@gmail.com","temp1234");
        Assert.assertTrue(landingPage.checkUserSignedIn());
    }

    @Test (expectedExceptions = {IllegalStateException.class})
    public void test_select_location_wrong_zipcode(){
      landingPage.selectLocation("1234");
    }
  //  @Test
//    public void test_round_float_values(){
//        CorePathTests tests = new CorePathTests();
//        Assert.assertEquals("24.56",t);
//    }
}
