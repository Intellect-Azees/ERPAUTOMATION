package Login;

import Base.LaunchBrowser;
import Utilities.HandelCaptcha;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ePACSLogin {
    @FindBy(id = "UserName")
    private WebElement userNameInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(id = "imgcapt")
    private WebElement captchaLabel;

    @FindBy(id = "ValidateCaptcha")
    private WebElement captchaInput;

    @FindBy(id = "btnView")
    private WebElement loginButton;
    
    @FindBy(xpath = "(//a[@class='dropdown-toggle'])[1]")
    private WebElement signoutMenu;
    
    @FindBy(xpath="//a[text()='Sign Out']")
    private WebElement signout;

    public WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public ePACSLogin() {
        LaunchBrowser.initializeDriver(); // Ensure WebDriver is initialized
        this.driver = LaunchBrowser.getDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String username, String password) {
        try {
            userNameInput.sendKeys(username);
            passwordInput.sendKeys(password);
            wait.until(ExpectedConditions.visibilityOf(captchaLabel));
            
            HandelCaptcha handelCaptcha = new HandelCaptcha(captchaLabel);
            if (handelCaptcha.captcha()) {
                captchaInput.clear();
                captchaInput.sendKeys(handelCaptcha.text);
            } else {
                throw new Exception("Failed to process Captcha");
            }

            // loginButton.click();  // Uncomment when captcha handling is fully functional
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void logout() throws Exception {
        signoutMenu.click();
        Thread.sleep(3000);
        signout.click();
    }
}
