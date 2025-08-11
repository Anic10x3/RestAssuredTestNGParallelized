package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager{
    private static ExtentReports extent;

    public static ExtentReports getInstance(){
        if(extent == null){
            createInstance();
        }
        return extent;
    }


    private static ExtentReports createInstance(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timeStamp + ".html";

        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("API Automation Test Report");
        reporter.config().setDocumentTitle("Extent Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Automation Team");
        return extent;
    }
}