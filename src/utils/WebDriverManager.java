package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebDriverManager {
    private static WebDriverManager instance; // Singleton instance
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String URL = "https://qa-tipalti-assignment.tipalti-pg.com/index.html";

    // Private constructor to prevent direct instantiation
    private WebDriverManager() {
        setupDriver();
    }

    // Get the Singleton instance of WebDriverManager (thread-safe)
    public static synchronized WebDriverManager getInstance() {
        if (instance == null) {
            instance = new WebDriverManager();
        }
        return instance;
    }

    // Initialize WebDriver if it's not already created
    private void setupDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // Open browser maximized
            options.addArguments("--disable-notifications"); // Disable pop-ups
            driver = new ChromeDriver(options); // Create new ChromeDriver with options
            wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Use the new WebDriverWait constructor
        }
    }

    // Get WebDriver instance
    public WebDriver getDriver() {
        return driver;
    }

    // Get WebDriverWait instance
    public WebDriverWait getWait() {
        return wait;
    }

    // Open the test website
    public void openSite() {
        driver.get(URL);
        wait.until(webDriver -> driver.getCurrentUrl().equals(URL));
    }

    // Wait until URL changes to contain expected part
    public void waitForUrlToChange(String expectedUrlPart) {
        wait.until(webDriver -> driver.getCurrentUrl().contains(expectedUrlPart));
    }

    // Close the browser and clean up WebDriver instance
    public void closeBrowser() {
        if (driver != null) {
            driver.quit(); // Properly quit the driver
            driver = null; // Reset WebDriver instance
        }
    }
}
