package Log;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import Base.LaunchBrowser;


public class ePACSLog implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println("Test failed: " + methodName);

        WebDriver driver = getWebDriverFromResult(result);

        if (driver != null) {
            try {
                String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                String screenshotPath = "data:image/png;base64," + screenshotBase64;

                Reporter.log("<br><b>Screenshot:</b></br>");
                Reporter.log("<a href='" + screenshotPath + "'><img src='" + screenshotPath + "' width='800' height='500'/></a>");
            } catch (Exception e) {
                System.out.println("Screenshot capture failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test failed but within success percentage: " + result.getName());
        logTestFailureWithinThreshold(result);
    }

    @Override
    public void onStart(ITestContext context) {
       // System.out.println("Test execution started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test execution finished: " + context.getName());
    }

    private void logTestFailureWithinThreshold(ITestResult result) {
        System.out.println("Logging failure within success threshold for test: " + result.getName());
    }

    // Utility method to get WebDriver instance from test result
    private WebDriver getWebDriverFromResult(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof LaunchBrowser) {
            return LaunchBrowser.driver;
        }
        return null;
    }
}
