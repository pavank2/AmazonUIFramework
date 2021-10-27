package com.amazon.qa.base;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.amazon.qa.factory.DriverFactory;
import com.amazon.qa.util.ConfigReader;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import com.amazon.qa.util.TestUtil;


import com.amazon.qa.util.TestUtil;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseTest {
    DriverFactory driverFactory;
    public WebDriver driver;
    Properties prop;
    private ConfigReader configReader;
    //  public static EventFiringWebDriver e_driver;
    //  public static WebEventListener listener;

    public BaseTest(){
        driver= DriverFactory.getDriver();
    }

    public void initialize() {
        configReader = new ConfigReader();
        prop = configReader.init_prop();
        String browserName = prop.getProperty("browser");
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browserName);

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--enable-automation");
//
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-extensions");
//        options.addArguments("--dns-prefetch-disable");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--headless");
//
//        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//        try {
//            driver = new RemoteWebDriver(new URL("http://65.2.73.249:30000/wd/hub"),options);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

    }

}

