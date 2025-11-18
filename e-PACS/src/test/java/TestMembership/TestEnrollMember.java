package TestMembership;

import Membership.EnrollMember;
import Membership.RegisterLandDetails;
import TestData.Data_Generator;
import TestData.Test_Data;
import TestLogin.TestePACSLogin;

import java.util.NoSuchElementException;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import Base.LaunchBrowser;
import DashBoard.Dashboard;

public class TestEnrollMember extends TestePACSLogin {

    private EnrollMember enrollMember;
    public Data_Generator datagen;
    Test_Data data=new Test_Data();
/*
 * Test Case = 01
 * 1.Create A new Member 
 * 2.Add land details 
 * 3.Perform share deposit transaction
 * 4.Authorize Voucher
 * 4.Add Identity details
 * 5.View Member
 */
    @Test(groups = {"Smoke"})
    public void TestCase1() throws Exception {
    	WebDriver driver = LaunchBrowser.driver; 
        Dashboard dashboard = new Dashboard(driver);
        RegisterLandDetails cusLand=new RegisterLandDetails(driver);
        enrollMember = new EnrollMember();
        datagen = new Data_Generator(); 

        dashboard.clickOkIfPresent();
        
        dashboard.navigateToMembership();

        enrollMember.enrollMember(data.dataUtility("TestData", 1, 1),
            datagen.getLastName(),
            datagen.getFirstName(),
            datagen.getMemberDOB(),
            datagen.getGender(),
            datagen.getFirstName(),
            datagen.houseNO(),
            datagen.emailAddress(),
            datagen.phoneNo(),
            data.dataUtility("TestData", 2, 1)
            );
        String str=enrollMember.lastSevenDigits;
        data.updateData("TestData", 0, 1, str);
        
        cusLand.registerLand(data.dataUtility("TestData", 0, 1),
        					datagen.getSurveyNumber()
        					);
//        data.dataUtility("TestData", 2, 1);
        
        dashboard.navigateToDashboard();
    }

//    @Test(groups= {"Smoke"})
//    public void TestCase2()
//    {
//    		System.out.print("TC2");
//    	
//    }
}
