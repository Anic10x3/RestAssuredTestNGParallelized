package listeners;

import com.aventstack.extentreports.Status;
import org.testng.*;
import utils.ExtentTestManager;
import utils.RetryAnalyzer;
import utils.ThreadLocalLogger;

public class TestNGListener implements ITestListener, ISuiteListener {
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result);
        if (result.getMethod().getRetryAnalyzer(result) == null) {
            result.getMethod().setRetryAnalyzerClass(RetryAnalyzer.class);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentTestManager.getTest()
                    .log(Status.PASS, "Test Passed");
            ThreadLocalLogger.removeLogger();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        IRetryAnalyzer retryAnalyzer = result.getMethod().getRetryAnalyzer(result);
        if (retryAnalyzer instanceof RetryAnalyzer) {
            int currentRetryCount = ((RetryAnalyzer) retryAnalyzer).getRetryCount();
            if (currentRetryCount < RetryAnalyzer.getMaxRetryCount()) {
                ExtentTestManager.getTest()
                        .log(Status.WARNING, "Test Failed, retrying... (Attempt "
                                + (currentRetryCount + 1) + "/" + RetryAnalyzer.getMaxRetryCount() + ")");
                return;
            }
        }
        ExtentTestManager.getTest()
                .log(Status.FAIL, "Test Failed: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest()
                .log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTestManager.flush();
    }

}
