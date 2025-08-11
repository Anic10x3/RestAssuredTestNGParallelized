import config.client.ApiClient;
import config.EnvironmentConfig;
import constants.Endpoints;
import constants.HttpMethod;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JSONPlaceHolderTest {
    @Test
    public void getPost() {
        Response response = ApiClient.sendRequest(EnvironmentConfig.JSONUrl(), Endpoints.POSTS, HttpMethod.GET);
        Assert.assertEquals(response.getStatusCode(), 2030, "Expected status code 200 but got " + response.getStatusCode());
    }
}
