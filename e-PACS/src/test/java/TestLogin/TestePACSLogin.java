package TestLogin;

import Login.ePACSLogin;
import TestData.Test_Data;
import Utilities.EmailSender;
import Utilities.ePACSInfo;
import Base.LaunchBrowser;
import Log.ePACSLog;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(ePACSLog.class)
public class TestePACSLogin {
	ePACSInfo log=new ePACSInfo();
    public ePACSLogin loginPage;
    public Test_Data testData = new Test_Data();
    
    @BeforeClass
    public void setup() throws Exception {
        LaunchBrowser.initializeDriver();  
        LaunchBrowser.openURL(testData.getLoginCredantials("URL"));
        loginPage = new ePACSLogin(); 
    }

    @BeforeMethod
    public void login() throws Exception {
        loginPage.login(testData.getLoginCredantials("UserName"), 
                testData.getLoginCredantials("Password"),
                testData.getLoginCredantials("LoginDate"));
    }

    //    @AfterMethod
    //    public void logout() throws Exception {
    //        loginPage.logout();
    //    }

    @AfterClass
    public void tearDown() {
        LaunchBrowser.closeBrowser();
        
        // Handle report file management automatically
        String reportPath = manageReportFile();
        
        List<String> teamEmails = Arrays.asList(
                "azees@intellectinfo.com"
        );

        // Send the report
        if (new File(reportPath).exists()) {
            EmailSender.sendTestReport(teamEmails, reportPath);
            log.error("✅ Report sent successfully to team");
            
        }
        else 
        {
            log.error("❌ Report file not found at: " + reportPath);
        }
    }
    
    /**
     * Automatically manages the report file:
     * 1. Deletes any existing report file
     * 2. Creates the required directory structure
     * 3. Returns the path where the new report will be generated
     */
    private String manageReportFile() {
        String basePath = System.getProperty("user.dir");
        String targetPath = basePath + "/test-output/ExtentReports/extentReports.html";
        String oldPath = basePath + "/ExtentReports/extentReports.html";
        
        // Delete old report file if it exists
        deleteFileIfExists(oldPath);
        
        // Delete target report file if it exists (to ensure fresh report)
        deleteFileIfExists(targetPath);
        
        // Create directory structure if it doesn't exist
        createDirectoryStructure(new File(basePath + "/test-output/ExtentReports"));

        log.info("📁 Report directory prepared: " + targetPath);
        return targetPath;
    }
    
    /**
     * Deletes a file if it exists
     */
    private void deleteFileIfExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                log.info("🗑️  Deleted existing report: " + filePath);
            } else {
                log.error("⚠️  Could not delete file: " + filePath);
            }
        }
    }
    
    /**
     * Creates directory structure recursively
     */
    private void createDirectoryStructure(File directory) {
        if (!directory.exists()) {
            if (directory.mkdirs()) 
            {
                log.info("📁 Created directory: " + directory.getAbsolutePath());
            } 
            	else 
            {
                log.error("❌ Failed to create directory: " + directory.getAbsolutePath());
            }
        }
    }
}