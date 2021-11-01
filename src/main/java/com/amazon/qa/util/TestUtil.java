package com.amazon.qa.util;

import com.amazon.qa.base.BaseTest;
import com.amazon.qa.factory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Utility methods common to all classes
 * @Author: PK
 */
public class TestUtil extends BaseTest {
    public static long PAGE_LOAD_TIMEOUT = 10;
    public static long IMPLICIT_WAIT = 10;
    static WebDriver driver = DriverFactory.getDriver();

    public static void sleepForNSeconds(int n){
        try {
            Thread.sleep(n*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void captureScreenShort(WebDriver driver, String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("./screenshot/" + fileName + new Date() + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkElementExists(List<WebElement> elements){
        if (elements.size() == 1 && elements.get(0).isDisplayed())
            return true;
        else
            return false;
    }

  public static void checkElementExistsAndClick(List<WebElement> elements){
        if (elements.size() > 0 && elements.get(0).isDisplayed()){
          elements.get(0).click();
      }
  }

    public static void waitForElement(By by) {
       // WebDriverWait w = new WebDriverWait(driver,10);
        //Declare and initialise a fluent wait
        Wait wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofSeconds(1))
            .ignoring(WebDriverException.class);

        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
