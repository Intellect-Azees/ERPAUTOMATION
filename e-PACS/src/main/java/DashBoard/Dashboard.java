package DashBoard;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Dashboard {

    private WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//a[@name='Membership']")
    private WebElement membershipMenu;

    @FindBy(xpath = "//a[@class='logo-a']")
    private WebElement homeMenu;
 
    By OK_BUTTON = By.xpath("//button[@class='confirm']");

    public Dashboard(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        PageFactory.initElements(driver, this);
    }

    public void navigateToMembership() {
        membershipMenu.click();
    }

    public void navigateToDashboard() {
        homeMenu.click();
    }

    public void clickOkIfPresent() 
    {
        try 
        {
        	List<WebElement> okButtons = driver.findElements(OK_BUTTON);

            if (!okButtons.isEmpty() && okButtons.get(0).isDisplayed()) {
                okButtons.get(0).click();
            }
         } 
        catch (NoSuchElementException | ElementNotInteractableException e) 
        {
         
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
