import config.client.ApiClient;
import config.EnvironmentConfig;
import constants.Endpoints;
import constants.HttpMethod;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StatusCode;

public class JSONPlaceHolderTest {
    @Test
    public void getPost() {
        Response response = ApiClient.sendRequest(EnvironmentConfig.JSONUrl(), Endpoints.POSTS, HttpMethod.GET);
        Assert.assertEquals(response.getStatusCode(), StatusCode.CODE_200.getCode(), "Expected status code 200 but got " + response.getStatusCode());
    }
}
