package tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import actions.Actions;
import utils.DynamicDataProvider;
import utils.WebDriverManager;

public class DogMenuTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private String title;
    private String baseTitle = "The Tipalti Dev-Dogs Foundation 👩‍💻";

    @BeforeClass
    public void setup() {
        driver = WebDriverManager.getInstance().getDriver(); // Get WebDriver from WebDriverManager
        this.wait = WebDriverManager.getInstance().getWait(); // Use WebDriverManager's wait
        actions = new Actions(driver); // Initialize Actions class with WebDriver
        WebDriverManager.getInstance().openSite(); // Open site
    }

    @AfterClass
    public void tearDown() {
        WebDriverManager.getInstance().closeBrowser(); // Ensure WebDriver instance is properly closed
    }

    @Test(dataProvider = "dogNamesProvider", dataProviderClass = DynamicDataProvider.class)
    public void testDogSelection(String dogName) throws Exception {
    	System.out.println("Testing with dog name: " + dogName);
    	assertTrue(driver.findElement(locators.Locators.MAIN_TITLE).isDisplayed(), "Element exists but is not visible!");
    	title = driver.findElement(locators.Locators.MAIN_TITLE).getText();
        Assert.assertEquals(title, baseTitle);
        

        // Step 1: Open the menu
        actions.openMenu();

        // Step 2: Select the dog option from the menu
        actions.selectDogOption(dogName);

        if (!dogName.equals("Home")) {
            // Step 3: Wait for the correct dog page URL
            wait.until(webDriver -> actions.isDogPageUrlCorrect(driver, dogName));
            title = driver.findElement(locators.Locators.MAIN_TITLE).getText();
            assertTrue(actions.isDogPageUrlCorrect(driver, dogName), "Error page was not displayed after form submission.");
            Assert.assertEquals(title, dogName);

            // Step 4: Fill out the contact form
            String uniqueMessage = "Hello, " + dogName + "! HAVE A BARKY DAY, WOOF!.";
            actions.fillContactForm(dogName, dogName.toLowerCase() + "@dogmail.com", uniqueMessage);

            // Step 5: Submit the form
            actions.sendContactForm();

            // Step 6: Validate that the error page is displayed
            boolean isErrorDisplayed = actions.isErrorPageDisplayed();
            assertTrue(isErrorDisplayed, "Error page was not displayed after form submission.");
            
        } else {
            actions.closeMenu();
            wait.until(webDriver -> actions.isDogPageUrlCorrect(driver, "index"));
            Assert.assertEquals(title, baseTitle);
            assertTrue(actions.isDogPageUrlCorrect(driver, "index"), "Error page was not displayed after form submission.");
        }
        WebDriverManager.getInstance().openSite(); // Open site
    }
}
