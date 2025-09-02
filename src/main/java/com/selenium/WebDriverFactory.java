package com.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * WebDriver factory for creating and managing WebDriver instances
 */
public class WebDriverFactory {
    
    public enum BrowserType {
        CHROME, FIREFOX, EDGE
    }
    
    /**
     * Create a WebDriver instance based on browser type
     * @param browserType The type of browser to create
     * @return WebDriver instance
     */
    public static WebDriver createDriver(BrowserType browserType) {
        return createDriver(browserType, false);
    }
    
    /**
     * Create a WebDriver instance based on browser type with headless option
     * @param browserType The type of browser to create
     * @param headless Whether to run in headless mode
     * @return WebDriver instance
     */
    public static WebDriver createDriver(BrowserType browserType, boolean headless) {
        WebDriver driver;
        
        switch (browserType) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                throw new IllegalArgumentException("Browser type not supported: " + browserType);
        }
        
        return driver;
    }
    
    /**
     * Create a Chrome WebDriver instance
     * @return Chrome WebDriver instance
     */
    public static WebDriver createChromeDriver() {
        return createDriver(BrowserType.CHROME);
    }
    
    /**
     * Create a Firefox WebDriver instance
     * @return Firefox WebDriver instance
     */
    public static WebDriver createFirefoxDriver() {
        return createDriver(BrowserType.FIREFOX);
    }
    
    /**
     * Create an Edge WebDriver instance
     * @return Edge WebDriver instance
     */
    public static WebDriver createEdgeDriver() {
        return createDriver(BrowserType.EDGE);
    }
    
    /**
     * Create a headless Chrome WebDriver instance
     * @return Headless Chrome WebDriver instance
     */
    public static WebDriver createHeadlessChromeDriver() {
        return createDriver(BrowserType.CHROME, true);
    }
    
    /**
     * Quit the WebDriver instance safely
     * @param driver The WebDriver instance to quit
     */
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}