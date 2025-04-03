package utils;

import java.util.List;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import actions.Actions;

public class MenuDataProvider {
    private WebDriver driver;
    private Actions actions;

    public MenuDataProvider(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public String[] getDogNamesFromMenu() throws Exception {
        // Open the menu
        actions.openMenu();
        
        // Get menu items
        List<WebElement> menuItems = actions.getMenuItems();
        List<String> dogNames = new ArrayList<>();

        for (WebElement item : menuItems) {
            dogNames.add(item.getText().trim());
        }

        System.out.println("Retrieved Dog Names:");
        for (String name : dogNames) {
            System.out.println(name);
        }

        actions.closeMenu();
        return dogNames.toArray(new String[0]);
    }
}
