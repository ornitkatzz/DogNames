package actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverManager;
import java.util.List;

public class Actions {

	private WebDriver driver;
	private WebDriverWait wait; // Now we'll get this from WebDriverManager
	private final String expectedUrlPrefix = "https://qa-tipalti-assignment.tipalti-pg.com/";

	public Actions(WebDriver driver) {
		this.driver = WebDriverManager.getInstance().getDriver(); // Get WebDriver instance from WebDriverManager
		this.wait = WebDriverManager.getInstance().getWait(); // Get WebDriverWait instance from WebDriverManager
	}

	public boolean isClickable(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isMenuOpen() {
		WebElement body = driver.findElement(locators.Locators.BODY);
		String classAttribute = body.getAttribute("class");
		return classAttribute.contains("is-menu-visible");
	}

	// Open the menu by clicking the hamburger button
	public void openMenu() throws Exception {
		
		if (!isMenuOpen()) {
			Thread.sleep(500);
			driver.findElement(locators.Locators.MENU_BUTTON).click();
			wait.until((WebDriver driver) -> isMenuOpen());
		}
	}

	// Close the menu by clicking the 'X' button
	public void closeMenu() throws Exception {
		if (isMenuOpen()) {
			Thread.sleep(500);
			driver.findElement(locators.Locators.X_BUTTON).click();
			wait.until((WebDriver driver) -> !isMenuOpen());
		}
	}

	// Get all menu items
	public List<WebElement> getMenuItems() throws Exception {
		wait.until((WebDriver driver) -> isMenuOpen());
		if (!isMenuOpen())
			openMenu();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(locators.Locators.MENU_ITEMS)));
		return driver.findElements(locators.Locators.MENU_ITEMS);
	}

	// Select a dog option from the menu
	public void selectDogOption(String dogName) throws Exception {
		if (!isMenuOpen())
			openMenu();
		List<WebElement> menuItems = getMenuItems();

		for (WebElement item : menuItems) {
			wait.until(ExpectedConditions.elementToBeClickable(item)); // Use wait from WebDriverManager
			if (item.getText().trim().equals(dogName)) {
				Thread.sleep(500);
				item.click();
				break;
			}
		}
	}

	// Fill out the contact form
	public void fillContactForm(String name, String email, String message) {
		WebElement nameField = driver.findElement(locators.Locators.NAME_INPUT);
		WebElement emailField = driver.findElement(locators.Locators.EMAIL_INPUT);
		WebElement messageField = driver.findElement(locators.Locators.MESSAGE_INPUT);

		nameField.sendKeys(name);
		emailField.sendKeys(email);
		messageField.sendKeys(message);
	}

	// Send the contact form
	public void sendContactForm() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locators.Locators.SEND_BUTTON))).click();
		Thread.sleep(500);
	}

	// Check if the error page is displayed
	public boolean isErrorPageDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(locators.Locators.ERROR_MSG)));
			return driver.findElement(locators.Locators.ERROR_MSG).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Validate the URL of the dog page
	public boolean isDogPageUrlCorrect(WebDriver driver, String dogName) {
		String expectedUrl = expectedUrlPrefix + dogName.toLowerCase() + ".html";
		wait.until(ExpectedConditions.urlToBe(expectedUrl));
		String currentUrl = driver.getCurrentUrl();
		return currentUrl.equals(expectedUrl);
	}

	// Extract text from a list of WebElements
	public String[] extractTexts(List<WebElement> elements) {
		return elements.stream().map(WebElement::getText).toArray(String[]::new);
	}
}
