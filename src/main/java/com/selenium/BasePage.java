package com.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * Base page class that provides common functionality for all page objects
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /**
     * Navigate to a specific URL
     * @param url The URL to navigate to
     */
    public void navigateTo(String url) {
        driver.get(url);
    }
    
    /**
     * Wait for an element to be visible and return it
     * @param locator The locator for the element
     * @return The WebElement once it's visible
     */
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Click on an element after waiting for it to be clickable
     * @param locator The locator for the element
     */
    public void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    /**
     * Enter text into an input field
     * @param locator The locator for the input field
     * @param text The text to enter
     */
    public void enterText(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get the text content of an element
     * @param locator The locator for the element
     * @return The text content of the element
     */
    public String getElementText(By locator) {
        return waitForElement(locator).getText();
    }
    
    /**
     * Check if an element is displayed
     * @param locator The locator for the element
     * @return true if element is displayed, false otherwise
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get the current page title
     * @return The page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}