package com.amazon.qa.pages;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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


    public SignInPage(WebDriver driver) {
     //   super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterEmailAndPassword(String mail,String pass){
        email.sendKeys(mail);
        continueButton.click();
        password.sendKeys(pass);
        signInButton.click();

        if (TestUtil.checkElementExists(wrongCredentialsMsg))
            throw new IllegalStateException("Wrong credentials entered");

    }

    public boolean checkUserSignedOut(){
        return continueButton.isDisplayed();
    }

}
