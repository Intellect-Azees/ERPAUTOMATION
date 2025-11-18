package Membership;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Base.LaunchBrowser;

public class RegisterLandDetails 
{
	Robot handelKey;
	
	@FindBy(xpath="//li[@id='10002']//a[contains(text(),'Register Land')]")
	public WebElement registerLandLink;
	
	@FindBy(xpath="//input[@id='Admissionno']")
	public WebElement admissionNoInput;
	
	@FindBy(id="AddLand")
	public WebElement addButton;
	
	@FindBy(id="SurveyNo")
	public WebElement surveyNoInput;
	
	@FindBy(id="TotalextentlandAcres")
	public WebElement acresInput;
	
//	@FindBy(xpath="((//span[@class='custom-combobox'])[4]/input")
//	public WebElement villageComboinput;
	
	By villageComboinput=By.xpath("((//span[@class='custom-combobox'])[4]/input");

private WebDriver driver;
	
	public RegisterLandDetails(WebDriver driver)
	{
		this.driver = driver;
		 PageFactory.initElements(driver, this);
	}
	public WebDriver getDriver() {
        return driver;
    }
	public void registerLand(String admissionNo,String SurveyNo) throws Exception
	{
		handelKey=new Robot();
		registerLandLink.click();
		admissionNoInput.sendKeys(admissionNo);
		
		handelKey.keyPress(KeyEvent.VK_TAB);
        handelKey.keyRelease(KeyEvent.VK_TAB);
		
        addButton.click();
//        Select vlg=new Select(villageComboinput);
//        vlg.selectByVisibleText(village);
        WebElement okButtons = driver.findElement(villageComboinput);
        
        handelKey.keyPress(KeyEvent.VK_ENTER);
        handelKey.keyRelease(KeyEvent.VK_ENTER);
        
        handelKey.keyPress(KeyEvent.VK_PAGE_DOWN);
        handelKey.keyRelease(KeyEvent.VK_PAGE_DOWN);
        
        handelKey.keyPress(KeyEvent.VK_PAGE_DOWN);
        handelKey.keyRelease(KeyEvent.VK_PAGE_DOWN);
        
        handelKey.keyPress(KeyEvent.VK_PAGE_DOWN);
        handelKey.keyRelease(KeyEvent.VK_PAGE_DOWN);
        
        handelKey.keyPress(KeyEvent.VK_PAGE_DOWN);
        handelKey.keyRelease(KeyEvent.VK_PAGE_DOWN);
        
        handelKey.keyPress(KeyEvent.VK_TAB);
        handelKey.keyRelease(KeyEvent.VK_TAB);
        
        surveyNoInput.sendKeys(SurveyNo);
	}

}
