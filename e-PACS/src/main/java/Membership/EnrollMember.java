package Membership;

import Base.LaunchBrowser;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

public class EnrollMember extends LaunchBrowser 
{
	public EnrollMember()
	{
	    PageFactory.initElements(LaunchBrowser.getDriver(), this);
	}

	SoftAssert ast=new SoftAssert();
	public String lastSevenDigits;
	
	@FindBy(xpath = "//li[@id='10001']//a[contains(text(),'Enroll Member')]")
    public WebElement enrollMemberLink;

    @FindBy(id = "CustomerType")
    public WebElement customerTypeDropdown;
    
    @FindBy(id = "MemberType")
    public WebElement memberType1;

    @FindBy(id = "GeneralDetailsDTO_SurName")
    public WebElement surnameInput;

    @FindBy(id = "GeneralDetailsDTO_Name")
    public WebElement nameInput;

    @FindBy(id = "GeneralDetailsDTO_AadhaarCardNo")
    public WebElement aadhaarCardInput;
    
    @FindBy(xpath="//input[@id='GeneralDetailsDTO_MemberFullName']")
    public WebElement fullName;

    @FindBy(id="GeneralDetailsDTO_NameAsPerAadhaar")
    public WebElement nameAsPeraadharInput;
    
    @FindBy(id="GeneralDetailsDTO_DateofBirth")
    public WebElement DOBInput;
    
    @FindBy(id="GeneralDetailsDTO_Age")
    public WebElement age;
    
    @FindBy(id="GeneralDetailsDTO_Gender")
    public WebElement genderInput;
    
    @FindBy(id="GeneralDetailsDTO_FatherName")
    public WebElement fatherNameInput;
    
    @FindBy(id="btnfinish")
    public WebElement nextButtonInput;
    
    @FindBy(id="AddressDTO_CommunicationHouseNo")
    public WebElement houseNOInput;
    
    @FindBy(xpath="(//span[@class='custom-combobox'])[1]")
    public WebElement villageInput;
    
    @FindBy(id="AddressDTO_Emailid")
    public WebElement emailInput;
    
    @FindBy(id="AddressDTO_MobileNo")
    public WebElement mobileNoInput;
    
    @FindBy(xpath="(//span[@class='text'])[1]")
    public WebElement addressDetailsCheckBoxInput;
    
    @FindBy(xpath="//button[@class='confirm']")
    public WebElement admissionNo;
    
    @FindBy(xpath="/html/body/div[8]/h2")
    public WebElement admissionNoVal;
    
    public void enrollMember(String memberType,String surname, String name,String memberDOB, String gender,String fatherName,
    		String houseNo, String cusemail, String cusMobileNo, String village) throws Exception {
    	Robot handelKey=new Robot();
        enrollMemberLink.click();
        Select selectedOption=new Select(customerTypeDropdown);
        selectedOption.selectByVisibleText(memberType);
        String selectedText = selectedOption.getFirstSelectedOption().getText();
        String nonMember="Nominal Member";
        String organisation="Organisation";
        if(selectedText.equals(nonMember))
        {
        	Select s1=new Select(memberType1);
        	s1.selectByVisibleText("B Class");
        }
        else if(selectedText.equals(organisation))
        {
        	Select s1=new Select(memberType1);
        	s1.selectByVisibleText("C Class");
        }
        surnameInput.sendKeys(surname);
        nameInput.sendKeys(name);
//        aadhaarCardInput.sendKeys(aadhaarCard);

        String memberfullName=fullName.getAttribute("value");
        nameAsPeraadharInput.sendKeys(memberfullName);
        DOBInput.sendKeys(memberDOB);
        
        handelKey.keyPress(KeyEvent.VK_TAB);
        handelKey.keyRelease(KeyEvent.VK_TAB);

        Select cusGender=new Select(genderInput);
        cusGender.selectByVisibleText(gender);
        fatherNameInput.sendKeys(fatherName);
        //nextButtonInput.click();
        handelKey.keyPress(KeyEvent.VK_ALT);
        handelKey.keyPress(KeyEvent.VK_N);
        
        handelKey.keyRelease(KeyEvent.VK_ALT);
        handelKey.keyRelease(KeyEvent.VK_N);
//        age.sendKeys(memberAge);
        houseNOInput.sendKeys(houseNo);
        Thread.sleep(1000);
        //villageInput.click();
        handelKey.keyPress(KeyEvent.VK_TAB);
        handelKey.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(1000);
        handelKey.keyPress(KeyEvent.VK_TAB);
        handelKey.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(1000);
        
        villageInput.sendKeys(village);
        
        
//        handelKey.keyPress(KeyEvent.VK_PAGE_DOWN);
//        handelKey.keyRelease(KeyEvent.VK_PAGE_DOWN);
//        
//        handelKey.keyPress(KeyEvent.VK_PAGE_DOWN);
//        handelKey.keyRelease(KeyEvent.VK_PAGE_DOWN);
//        
        handelKey.keyPress(KeyEvent.VK_TAB);
        handelKey.keyRelease(KeyEvent.VK_TAB);
        
        emailInput.sendKeys(cusemail);
        mobileNoInput.sendKeys(cusMobileNo);
        addressDetailsCheckBoxInput.click();
        
        handelKey.keyPress(KeyEvent.VK_ALT);
        handelKey.keyPress(KeyEvent.VK_N);
        
        handelKey.keyRelease(KeyEvent.VK_ALT);
        handelKey.keyRelease(KeyEvent.VK_N);
        
        handelKey.keyPress(KeyEvent.VK_ALT);
        handelKey.keyPress(KeyEvent.VK_N);
        
        handelKey.keyRelease(KeyEvent.VK_ALT);
        handelKey.keyRelease(KeyEvent.VK_N);
        
        handelKey.keyPress(KeyEvent.VK_ALT);
        handelKey.keyPress(KeyEvent.VK_N);
        
        handelKey.keyRelease(KeyEvent.VK_ALT);
        handelKey.keyRelease(KeyEvent.VK_N);
        if(admissionNo.isDisplayed())
        {
        	String adNo=admissionNoVal.getText();
        	lastSevenDigits = adNo.substring(adNo.length() - 7);
        	admissionNo.click();
        	ast.assertTrue(true);
        }
        else
        {
        	ast.fail("Unable to find the scusses popup");
        }
    }
}
