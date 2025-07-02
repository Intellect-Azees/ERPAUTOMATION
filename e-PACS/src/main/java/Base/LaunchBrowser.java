package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class LaunchBrowser {
    public static WebDriver driver;

    // Initialize WebDriver (singleton pattern)
    public static void initializeDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().window().maximize();
        }
    }

   
    public static void openURL(String url) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        driver.get(url);
    }

    // Close browser safely
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null; // Prevent memory leaks
            
        }
    }

    // Getter for WebDriver
    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        return driver;
    }
}