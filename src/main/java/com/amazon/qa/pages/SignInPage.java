package com.amazon.qa.pages;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Page objects and methods for Sign in Page
 * @Author: PK
 */
public class SignInPage extends BaseTest {
 WebDriver driver;
    @FindBy(id="ap_email")
    private WebElement email;

    @FindBy(id="continue")
    private WebElement continueButton;

    @FindBy(id="ap_password")
    private WebElement password;

    @FindBy(id="signInSubmit")
    private WebElement signInButton;

    @FindBy(id="auth-error-message-box")
    private List<WebElement> wrongCredentialsMsg;

    @FindBy(xpath="//h4[contains(text(),'Important Message!')]")
    private List<WebElement> wrongCredentialsMsg2;


    public SignInPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterEmailAndPassword(String mail,String pass){
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        email.sendKeys(mail);
        continueButton.click();
        password.sendKeys(pass);
        signInButton.click();

        if (TestUtil.checkElementExists(wrongCredentialsMsg) || TestUtil.checkElementExists(wrongCredentialsMsg2))
            throw new IllegalStateException("Wrong credentials entered");

    }

    public boolean checkUserSignedOut(){
        return continueButton.isDisplayed();
    }

}
