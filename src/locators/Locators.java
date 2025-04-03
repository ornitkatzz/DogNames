package locators;

import org.openqa.selenium.By;

public class Locators {
	public static final By MAIN_TITLE = By.tagName("h1");
    public static final By BODY = By.tagName("body");
    public static final By MENU_BUTTON = By.xpath("//div/nav/ul/li/a");
    public static final By X_BUTTON = By.className("close");
    public static final By MENU = By.id("menu");
    public static final By MENU_ITEMS = By.xpath("//*[@id=\"menu\"]/div/ul/li");
    public static final By MAIN_MENU_ITEMS = By.xpath("//*[@id=\"main\"]/div/section/article");
    public static final By NAME_INPUT = By.id("name");
    public static final By EMAIL_INPUT = By.id("email");
    public static final By MESSAGE_INPUT = By.id("message");
    public static final By SEND_BUTTON = By.className("primary");
    public static final By ERROR_MSG = By.xpath("//*[text()='This XML file does not appear to have any style information associated with it. The document tree is shown below.']");
}