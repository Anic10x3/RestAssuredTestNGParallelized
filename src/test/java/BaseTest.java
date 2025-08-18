import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.ThreadLocalLogger;

import java.lang.reflect.Method;

public class BaseTest {

    protected Logger log;
    @BeforeMethod(alwaysRun = true)
    public void setUpLogger(Method method){
       ThreadLocalLogger.initLogger(method.getDeclaringClass());
       log = ThreadLocalLogger.getLogger();
        log.info("****===== Starting test: {} ====***", method.getName());
    }

    @AfterMethod
    public void cleanUpLogger(Method method) {
        log.info("===== Finished test: {} =====", method.getName());
        ThreadLocalLogger.removeLogger();
    }

}
