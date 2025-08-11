package core;

import config.EnvironmentConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestSpecManager {

    private static final ThreadLocal<RequestSpecification> requestSpecThreadLocal = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(RequestSpecManager.class);

    private RequestSpecManager(){}

    public static RequestSpecification getRequestSpec(String baseURI){
        if(requestSpecThreadLocal.get() == null){
            RequestSpecBuilder builder = new RequestSpecBuilder()
                    .setBaseUri(baseURI)
                    .setRelaxedHTTPSValidation()
                    .setConfig(io.restassured.RestAssured.config().httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                            .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, EnvironmentConfig.timeout() * 1000)));

            RequestSpecification spec = builder.build().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter());
            requestSpecThreadLocal.set(spec);
            logger.info("Initialized RequestSpecification for thread: {}", Thread.currentThread().getName());
       }
        return requestSpecThreadLocal.get();
    }
    public static void remove() { requestSpecThreadLocal.remove(); }

}
