package utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

public class DynamicDataProvider {
    private MenuDataProvider menuDataProvider;

    // Default constructor required by TestNG
    public DynamicDataProvider() {
        WebDriver driver = WebDriverManager.getInstance().getDriver(); // Updated to new WebDriverManager
        this.menuDataProvider = new MenuDataProvider(driver);
    }

    @DataProvider(name = "dogNamesProvider")
    public Object[][] provideDogNames() throws Exception {
        String[] dogNames = menuDataProvider.getDogNamesFromMenu();

        // Convert String[] to Object[][] for TestNG DataProvider
        Object[][] data = new Object[dogNames.length][1];
        for (int i = 0; i < dogNames.length; i++) {
            data[i][0] = dogNames[i];
        }
        return data;
    }
}
