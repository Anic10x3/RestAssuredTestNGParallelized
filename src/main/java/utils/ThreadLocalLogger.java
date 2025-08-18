package utils;

import com.aventstack.extentreports.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.LogManager;

public class ThreadLocalLogger {

    private static final ThreadLocal<Logger> threadLocalLogger = new ThreadLocal<>();

    private ThreadLocalLogger() {
    }

    public static void initLogger(Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);
        threadLocalLogger.set(logger);
    }

    public static Logger getLogger() {
        return threadLocalLogger.get();
    }

    public static void removeLogger() {
        threadLocalLogger.remove();
    }

}
