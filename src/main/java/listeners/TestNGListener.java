package listeners;

import com.aventstack.extentreports.Status;
import org.testng.*;
import utils.ExtentTestManager;
import utils.RetryAnalyzer;
import utils.ThreadLocalLogger;

public class TestNGListener implements ITestListener, ISuiteListener {

   /* @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        ExtentTestManager.startTest(testName, description != null ? description : "");
        if(result.getMethod().getRetryAnalyzer(result) == null){
            result.getMethod().setRetryAnalyzerClass(utils.RetryAnalyzer.class);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if(result.getStatus() == ITestResult.SUCCESS) {
            if (ExtentTestManager.getTest(result.getMethod().getMethodName()) != null) {
                ExtentTestManager.getTest(result.getMethod().getMethodName())
                        .log(Status.PASS, "Test Passed");
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
       // if (ExtentTestManager.getTest(result.getMethod().getMethodName()) != null) {
            IRetryAnalyzer retryAnalyzer = result.getMethod().getRetryAnalyzer(result);
            if(retryAnalyzer instanceof RetryAnalyzer){
                int currentRetryCount = ((RetryAnalyzer) retryAnalyzer).getRetryCount();
                if(currentRetryCount < RetryAnalyzer.getMaxRetryCount()){
                    ExtentTestManager.getTest(result.getMethod().getMethodName())
                            .log(Status.WARNING, "Test Failed, retrying... (Attempt " + (currentRetryCount + 1) + "/" + RetryAnalyzer.getMaxRetryCount() + ")");
                    return;
                }
           // }

            ExtentTestManager.getTest(result.getMethod().getMethodName())
                    .log(Status.FAIL, "Test Failed: " + result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTestManager.flush();
    }*/

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
       // ThreadLocalLogger.initLogger(testName); // Initialize logger for the test
       // ThreadLocalLogger.getLogger().info("Starting test: {}", testName);
        String description = result.getMethod().getDescription();
        ExtentTestManager.startTest(result); // pass ITestResult, thread-safe
        if (result.getMethod().getRetryAnalyzer(result) == null) {
            result.getMethod().setRetryAnalyzerClass(RetryAnalyzer.class);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
          //  ThreadLocalLogger.getLogger().info("Test passed: {}", result.getMethod().getMethodName());
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
                return; // Don't mark as FAIL yet, because we are retrying
            }
        }
      //  ThreadLocalLogger.getLogger().error("Test failed: {}", result.getThrowable().getMessage());
        // Final failure after all retries
        ExtentTestManager.getTest()
                .log(Status.FAIL, "Test Failed: " + result.getThrowable());
       // ThreadLocalLogger.removeLogger();
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
