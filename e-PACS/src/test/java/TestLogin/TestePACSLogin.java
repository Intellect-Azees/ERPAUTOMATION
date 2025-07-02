package TestLogin;

import Login.ePACSLogin;
import Base.LaunchBrowser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class TestePACSLogin {
    public ePACSLogin loginPage;

    @BeforeClass
    public void setup() {
        LaunchBrowser.initializeDriver();  // Initialize WebDriver
        LaunchBrowser.openURL("http://183.82.113.243:7133/");
        loginPage = new ePACSLogin(); // Instantiate only after WebDriver is initialized
    }

    @BeforeMethod
    public void login() throws Exception {
        loginPage.login("91201508082001", "Root@123");
    }

//    @AfterMethod
//    public void logout() throws Exception {
//        loginPage.logout();
//    }
//
//    @AfterClass
//    public void tearDown() {
//        LaunchBrowser.closeBrowser();
//    }
}
