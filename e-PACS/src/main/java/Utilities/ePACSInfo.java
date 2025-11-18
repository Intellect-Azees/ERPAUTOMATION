package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.Reporter;

/**
 * FrameworkLogger: Utility to log messages throughout the project
 * Logs to TestNG Reporter and optionally to ExtentReports
 */
public class ePACSInfo {

    private static ExtentTest frameworkLogTest;

    // Initialize with ExtentTest (optional)
    public  void initialize(ExtentTest extentTest) {
        frameworkLogTest = extentTest;
    }

    // Log info messages
    public  void info(String message) {
        Reporter.log(message, true); // logs to TestNG reporter + console
        if (frameworkLogTest != null) {
            frameworkLogTest.info(message);
        }
    }

    // Log error messages
    public  void error(String message) {
        Reporter.log(message, true);
        if (frameworkLogTest != null) {
            frameworkLogTest.fail(message);
        }
    }

    // Log warning messages
    public  void warn(String message) {
        Reporter.log(message, true);
        if (frameworkLogTest != null) {
            frameworkLogTest.warning(message);
        }
    }
}
