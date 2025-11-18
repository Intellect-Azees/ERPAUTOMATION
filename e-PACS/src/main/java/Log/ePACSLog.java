package Log;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import Utilities.ePACSInfo;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import org.testng.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class ePACSLog implements ITestListener {
	ePACSInfo log=new ePACSInfo();

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static String extentReportPath; // HTML report path

    private static java.util.List<String[]> pdfResults = new ArrayList<>();
    private static int passed = 0, failed = 0, skipped = 0;

    private String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    @Override
    public void onStart(ITestContext context) {
        // Create Reports directory structure
        createDirectoryStructure();
        
        String reportName = "Execution_Report_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        extentReportPath = System.getProperty("user.dir") + "/test-output/ExtentReports/" + reportName + ".html";
        
        // Also create a static link for email
        createStaticReportLink(reportName);

        ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Optional: Add System info
        extent.setSystemInfo("Project", "e-PACS Automation");
        extent.setSystemInfo("Tester", "Automation Framework");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Report Path", extentReportPath);
    }

    /**
     * Creates the complete directory structure for reports
     */
    private void createDirectoryStructure() {
        String basePath = System.getProperty("user.dir");
        File reportsDir = new File(basePath + "/test-output/ExtentReports");
        File oldReportsDir = new File(basePath + "/Reports");
        
        // Clean up old Reports directory if it exists
        if (oldReportsDir.exists()) {
            deleteDirectory(oldReportsDir);
        }
        
        // Create new directory structure
        if (!reportsDir.exists()) 
        {
            if (reportsDir.mkdirs()) 
            {
                log.info("📁 Created report directory: " + reportsDir.getAbsolutePath());
            } 
            else 
            {
                log.error("❌ Failed to create report directory: " + reportsDir.getAbsolutePath());
            }
        }
    }
    
    /**
     * Creates a static symlink to the latest report for email
     */
    private void createStaticReportLink(String reportName) {
        try {
            String basePath = System.getProperty("user.dir");
            String staticPath = basePath + "/test-output/ExtentReports/extentReports.html";
            String actualPath = basePath + "/test-output/ExtentReports/" + reportName + ".html";
            
            // Delete existing static link if it exists
            File staticFile = new File(staticPath);
            if (staticFile.exists()) {
                staticFile.delete();
            }
            
            // Create symlink (copy for simplicity in Java)
            File actualFile = new File(actualPath);
            if (actualFile.exists()) {
                // For now, we'll just use the actual path
                // In a real scenario, you might want to create a symlink
                log.info("🔗 Static report link: " + staticPath);
                log.info("📄 Actual report: " + actualPath);
            }
        } 
        catch (Exception e) 
        {
            log.error("⚠️ Could not create static report link: " + e.getMessage());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create test instance when test starts
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest testNode = test.get();
        if (testNode != null) {
            testNode.pass("✅ Test Passed");
        }
        passed++;

        pdfResults.add(new String[]{
                result.getMethod().getMethodName(),
                "PASSED",
                getCurrentTime(),
                "-"
        });
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest testNode = test.get();
        if (testNode != null) {
            testNode.fail("❌ Test Failed: " + result.getThrowable());
        }
        failed++;

        pdfResults.add(new String[]{
                result.getMethod().getMethodName(),
                "FAILED",
                getCurrentTime(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "-"
        });
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest testNode = test.get();
        if (testNode != null) {
            testNode.skip("⚠️ Test Skipped: " + 
                (result.getThrowable() != null ? result.getThrowable().getMessage() : ""));
        }
        skipped++;

        pdfResults.add(new String[]{
                result.getMethod().getMethodName(),
                "SKIPPED",
                getCurrentTime(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "-"
        });
    }

    @Override
    public void onFinish(ITestContext context) {
        // Add summary to Extent Report
        ExtentTest summaryTest = extent.createTest("Test Summary");
        summaryTest.info("Total Tests: " + (passed + failed + skipped));
        summaryTest.info("Passed: " + passed);
        summaryTest.info("Failed: " + failed);
        summaryTest.info("Skipped: " + skipped);
        summaryTest.info("Report Location: " + extentReportPath);
        
        extent.flush(); // HTML Extent Report
        generatePDFReport(); // PDF Report
        
        // Create static link to latest report
        createStaticReportLink(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
        
        // Clean up
        test.remove();
        pdfResults.clear();
        passed = 0;
        failed = 0;
        skipped = 0;

        log.info("📊 Reports generated successfully!");
        log.info("📄 HTML Report: " + extentReportPath);
    }

    /**
     * Deletes a directory and all its contents
     */
    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }

    // Generate PDF Report
    private void generatePDFReport() {
        try {
            String basePath = System.getProperty("user.dir");
            String pdfPath = basePath + "/test-output/ExtentReports/TestDashboardReport_" +
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            Paragraph title = new Paragraph("Automation Test Execution Report")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Generation date
            Paragraph datePara = new Paragraph("Generated on: " + 
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .setFontSize(10)
                .setItalic();
            document.add(datePara);
            document.add(new Paragraph("\n"));

            // Summary Table
            float[] summaryColumnWidths = {1f, 1f};
            Table summary = new Table(UnitValue.createPercentArray(summaryColumnWidths))
                    .useAllAvailableWidth();
            
            // Header row
            Cell totalHeader = new Cell(1, 2)
                .add(new Paragraph("EXECUTION SUMMARY").setBold().setFontSize(14))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);
            summary.addCell(totalHeader);

            summary.addCell(createSummaryCell("Total Tests", ColorConstants.LIGHT_GRAY));
            summary.addCell(createSummaryCell(String.valueOf(passed + failed + skipped), ColorConstants.WHITE));
            
            summary.addCell(createSummaryCell("Passed", ColorConstants.GREEN));
            summary.addCell(createSummaryCell(String.valueOf(passed), ColorConstants.WHITE));
            
            summary.addCell(createSummaryCell("Failed", ColorConstants.RED));
            summary.addCell(createSummaryCell(String.valueOf(failed), ColorConstants.WHITE));
            
            summary.addCell(createSummaryCell("Skipped", ColorConstants.YELLOW));
            summary.addCell(createSummaryCell(String.valueOf(skipped), ColorConstants.WHITE));

            document.add(summary);
            document.add(new Paragraph("\n"));

            // Detailed Results Section
            Paragraph detailsHeader = new Paragraph("DETAILED TEST RESULTS")
                    .setBold()
                    .setFontSize(14)
                    .setPaddingBottom(10);
            document.add(detailsHeader);

            // Detailed Results Table
            if (!pdfResults.isEmpty()) {
                float[] detailColumnWidths = {4f, 2f, 2f, 6f};
                Table details = new Table(UnitValue.createPercentArray(detailColumnWidths))
                        .useAllAvailableWidth();
                
                // Header row
                details.addHeaderCell("Test Case");
                details.addHeaderCell("Status");
                details.addHeaderCell("Execution Time");
                details.addHeaderCell("Error Message");

                for (String[] row : pdfResults) {
                    details.addCell(new Cell().add(new Paragraph(row[0])));
                    details.addCell(createStatusCell(row[1]));
                    details.addCell(new Cell().add(new Paragraph(row[2])));
                    details.addCell(new Cell().add(new Paragraph(row[3] != null ? row[3] : "-")));
                }
                document.add(details);
            } else {
                document.add(new Paragraph("No test results available.").setItalic());
            }

            document.close();
            log.info("✅ PDF Report generated at: " + pdfPath);

        } 
        catch (Exception e) 
        {
            log.error("❌ Error generating PDF report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to create summary cells
    private Cell createSummaryCell(String text, Color backgroundColor) {
        return new Cell()
                .add(new Paragraph(text))
                .setBackgroundColor(backgroundColor)
                .setPadding(5);
    }

    // Helper method to create status cells with colors
    private Cell createStatusCell(String status) {
        Color statusColor = getStatusColor(status);
        return new Cell()
                .add(new Paragraph(status))
                .setBackgroundColor(statusColor)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5);
    }

    // Helper method to get color based on status
    private Color getStatusColor(String status) {
        switch (status.toUpperCase()) {
            case "PASSED": return ColorConstants.GREEN;
            case "FAILED": return ColorConstants.RED;
            case "SKIPPED": return ColorConstants.YELLOW;
            default: return ColorConstants.BLACK;
        }
    }
}