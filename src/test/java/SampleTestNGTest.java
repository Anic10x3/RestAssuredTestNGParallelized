import com.aventstack.extentreports.model.Log;
import config.client.ApiClient;
import config.EnvironmentConfig;
import constants.Endpoints;
import constants.HttpMethod;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.HeaderProvider;
import utils.StatusCode;
import utils.ThreadLocalLogger;

import java.util.HashMap;

public class SampleTestNGTest extends BaseTest {

    @Test(groups = {"user-service", "smoke"})
    public void getUsers() {
        Response response = ApiClient.sendRequestWithHeaders(EnvironmentConfig.baseUrl(), Endpoints.USERS, HttpMethod.GET, HeaderProvider.getDefaultHeaders());
        log.info("Recieved response");
        Assert.assertEquals(response.getStatusCode(), StatusCode.CODE_200.getCode());
    }

    @Test(groups = {"user-service", "regression"})
    public void getUsersWithId() {
        Response response = ApiClient.sendRequestWithHeaders(EnvironmentConfig.baseUrl(),Endpoints.USERS_WITH_ID,  HttpMethod.GET, HeaderProvider.getDefaultHeaders());
        Assert.assertEquals(response.getStatusCode(), StatusCode.CODE_200.getCode());

        log.info("Validating JSON");
        JsonPath jsonPath = new JsonPath(response.asString());
        String name =  jsonPath.getString("data.first_name");
        String lastName = jsonPath.getString("data.last_name");
        String id = jsonPath.getString("data.id");
        String email = jsonPath.getString("data.email");

        Assert.assertEquals(name, "Janet", "Name is not matching");
        Assert.assertEquals(lastName, "Weaver", "Last name is not matching");
        Assert.assertEquals(id, "2", "ID is not matching");
        Assert.assertEquals(email, "janet.weaver@reqres.in", "Email is not matching" );
    }

    @Test(groups = {"user-service", "regression"})
    public void getNonFoundUsers() {
        Response response = ApiClient.sendRequestWithHeaders(EnvironmentConfig.baseUrl(),Endpoints.USERS_NOT_FOUND,  HttpMethod.GET, HeaderProvider.getDefaultHeaders());
        log.info("Verifying status code");
        Assert.assertEquals(response.getStatusCode(),StatusCode.CODE_404.getCode());
    }
}
