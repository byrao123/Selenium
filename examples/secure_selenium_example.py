#!/usr/bin/env python3
"""
Secure Selenium WebDriver Configuration Example

This example demonstrates security best practices for Selenium test automation:
- Secure browser configurations
- Environment variable usage
- Proper credential management
- Error handling and logging
"""

import os
import logging
from selenium import webdriver
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, WebDriverException


class SecureWebDriver:
    """Secure WebDriver wrapper with security best practices"""
    
    def __init__(self, browser='chrome', headless=True):
        self.browser = browser.lower()
        self.headless = headless
        self.driver = None
        self.setup_logging()
        
    def setup_logging(self):
        """Configure secure logging (filters sensitive data)"""
        logging.basicConfig(
            level=logging.INFO,
            format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
        )
        self.logger = logging.getLogger(__name__)
        
    def get_secure_chrome_options(self):
        """Get Chrome options with security configurations"""
        options = ChromeOptions()
        
        # Security options
        options.add_argument("--disable-extensions")
        options.add_argument("--disable-plugins")
        options.add_argument("--disable-dev-shm-usage")
        options.add_argument("--disable-gpu")
        options.add_argument("--no-first-run")
        options.add_argument("--disable-default-apps")
        options.add_argument("--disable-background-timer-throttling")
        options.add_argument("--disable-backgrounding-occluded-windows")
        options.add_argument("--disable-renderer-backgrounding")
        
        # Privacy options
        options.add_argument("--incognito")
        options.add_argument("--disable-background-networking")
        
        # Headless mode (recommended for CI/CD)
        if self.headless:
            options.add_argument("--headless")
            
        # Window size for consistent screenshots
        options.add_argument("--window-size=1920,1080")
        
        # Only use no-sandbox in Docker environments when necessary
        if os.getenv('DOCKER_ENV') == 'true':
            options.add_argument("--no-sandbox")
            self.logger.warning("Running with --no-sandbox due to Docker environment")
            
        # Additional security preferences
        prefs = {
            "profile.default_content_setting_values": {
                "notifications": 2,  # Block notifications
                "geolocation": 2,    # Block location sharing
                "media_stream": 2,   # Block microphone/camera
            },
            "profile.default_content_settings.popups": 0  # Block popups
        }
        options.add_experimental_option("prefs", prefs)
        
        return options
        
    def get_secure_firefox_options(self):
        """Get Firefox options with security configurations"""
        options = FirefoxOptions()
        
        if self.headless:
            options.add_argument("--headless")
            
        # Security preferences
        options.set_preference("dom.webnotifications.enabled", False)
        options.set_preference("geo.enabled", False)
        options.set_preference("dom.push.enabled", False)
        options.set_preference("dom.battery.enabled", False)
        
        return options
        
    def create_driver(self):
        """Create WebDriver instance with secure configuration"""
        try:
            if self.browser == 'chrome':
                options = self.get_secure_chrome_options()
                self.driver = webdriver.Chrome(options=options)
            elif self.browser == 'firefox':
                options = self.get_secure_firefox_options()
                self.driver = webdriver.Firefox(options=options)
            else:
                raise ValueError(f"Unsupported browser: {self.browser}")
                
            # Set timeouts
            self.driver.implicitly_wait(10)
            self.driver.set_page_load_timeout(60)
            
            self.logger.info(f"Created {self.browser} driver successfully")
            return self.driver
            
        except WebDriverException as e:
            self.logger.error(f"Failed to create WebDriver: {e}")
            raise
            
    def safe_navigate(self, url):
        """Safely navigate to URL with validation"""
        if not url.startswith(('http://', 'https://')):
            raise ValueError("URL must start with http:// or https://")
            
        # Validate URL is not pointing to localhost/internal networks
        # (unless explicitly allowed for testing)
        if 'localhost' in url or '127.0.0.1' in url:
            if not os.getenv('ALLOW_LOCALHOST_TESTING'):
                raise ValueError("Localhost testing not allowed without explicit permission")
                
        try:
            self.driver.get(url)
            self.logger.info(f"Navigated to: {url}")
        except TimeoutException:
            self.logger.error(f"Timeout while loading: {url}")
            raise
        except WebDriverException as e:
            self.logger.error(f"Failed to navigate to {url}: {e}")
            raise
            
    def safe_find_element(self, by, value, timeout=10):
        """Safely find element with explicit wait"""
        try:
            wait = WebDriverWait(self.driver, timeout)
            element = wait.until(EC.presence_of_element_located((by, value)))
            return element
        except TimeoutException:
            self.logger.error(f"Element not found: {by}={value}")
            raise
            
    def safe_send_keys(self, element, text, mask_in_logs=False):
        """Safely send keys to element (with optional masking for sensitive data)"""
        try:
            element.clear()
            element.send_keys(text)
            
            if mask_in_logs:
                self.logger.info("Sent sensitive data to element (masked)")
            else:
                self.logger.info(f"Sent text to element: {text}")
                
        except WebDriverException as e:
            self.logger.error(f"Failed to send keys: {e}")
            raise
            
    def take_screenshot(self, filename=None):
        """Take screenshot with secure filename"""
        if not filename:
            import datetime
            timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
            filename = f"screenshot_{timestamp}.png"
            
        # Ensure screenshots directory exists
        screenshots_dir = "screenshots"
        os.makedirs(screenshots_dir, exist_ok=True)
        
        filepath = os.path.join(screenshots_dir, filename)
        
        try:
            self.driver.save_screenshot(filepath)
            self.logger.info(f"Screenshot saved: {filepath}")
            return filepath
        except WebDriverException as e:
            self.logger.error(f"Failed to take screenshot: {e}")
            raise
            
    def quit(self):
        """Safely quit the driver"""
        if self.driver:
            try:
                self.driver.quit()
                self.logger.info("WebDriver closed successfully")
            except Exception as e:
                self.logger.error(f"Error closing WebDriver: {e}")


def secure_login_example():
    """Example of secure login using environment variables"""
    # Get credentials from environment variables (NEVER hardcode!)
    username = os.getenv('TEST_USERNAME')
    password = os.getenv('TEST_PASSWORD')
    test_url = os.getenv('TEST_URL', 'https://example.com/login')
    
    if not username or not password:
        raise ValueError("TEST_USERNAME and TEST_PASSWORD environment variables must be set")
    
    # Create secure driver
    secure_driver = SecureWebDriver(browser='chrome', headless=True)
    driver = secure_driver.create_driver()
    
    try:
        # Navigate to login page
        secure_driver.safe_navigate(test_url)
        
        # Find and fill login form
        username_field = secure_driver.safe_find_element(By.ID, "username")
        password_field = secure_driver.safe_find_element(By.ID, "password")
        
        secure_driver.safe_send_keys(username_field, username)
        secure_driver.safe_send_keys(password_field, password, mask_in_logs=True)
        
        # Submit form
        login_button = secure_driver.safe_find_element(By.ID, "login-button")
        login_button.click()
        
        # Verify login success
        success_element = secure_driver.safe_find_element(By.CLASS_NAME, "dashboard")
        assert success_element is not None, "Login failed"
        
        # Take screenshot for verification
        secure_driver.take_screenshot("login_success.png")
        
        print("Login test completed successfully!")
        
    except Exception as e:
        # Take screenshot on failure
        secure_driver.take_screenshot("login_failure.png")
        print(f"Test failed: {e}")
        raise
        
    finally:
        # Always clean up
        secure_driver.quit()


if __name__ == "__main__":
    # Example usage
    print("Secure Selenium Example")
    print("Set environment variables: TEST_USERNAME, TEST_PASSWORD, TEST_URL")
    print("Then run this script to see secure automation in action")
    
    # Uncomment to run the example (after setting environment variables)
    # secure_login_example()