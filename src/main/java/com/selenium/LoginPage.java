package com.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Login page object class
 */
public class LoginPage extends BasePage {
    
    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.className("error-message");
    private final By welcomeMessage = By.className("welcome-message");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to the login page
     * @param baseUrl The base URL of the application
     */
    public void goToLoginPage(String baseUrl) {
        navigateTo(baseUrl + "/login");
    }
    
    /**
     * Enter username in the username field
     * @param username The username to enter
     */
    public void enterUsername(String username) {
        enterText(usernameField, username);
    }
    
    /**
     * Enter password in the password field
     * @param password The password to enter
     */
    public void enterPassword(String password) {
        enterText(passwordField, password);
    }
    
    /**
     * Click the login button
     */
    public void clickLoginButton() {
        clickElement(loginButton);
    }
    
    /**
     * Perform complete login action
     * @param username The username
     * @param password The password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Get error message text
     * @return The error message text
     */
    public String getErrorMessage() {
        return getElementText(errorMessage);
    }
    
    /**
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    /**
     * Get welcome message text
     * @return The welcome message text
     */
    public String getWelcomeMessage() {
        return getElementText(welcomeMessage);
    }
    
    /**
     * Check if welcome message is displayed
     * @return true if welcome message is displayed
     */
    public boolean isWelcomeMessageDisplayed() {
        return isElementDisplayed(welcomeMessage);
        return isElementDisplayed(welcomeMessage);
    }
    
    /**
     * Check if login button is enabled
     * @return true if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return waitForElement(loginButton).isEnabled();
    }
}
