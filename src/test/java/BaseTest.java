import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.ThreadLocalLogger;

public class BaseTest {

    @BeforeMethod
    public void setUpLogger(Object[] testArgs){
        String testName = this.getClass().getSimpleName();
        ThreadLocalLogger.initLogger(testName);
    }

    @AfterMethod
    public void cleanUpLogger() {
        ThreadLocalLogger.removeLogger();
    }
}
