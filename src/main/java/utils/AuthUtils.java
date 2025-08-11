package utils;
import config.EnvironmentConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import models.AuthResponse;

public class AuthUtils {
    private AuthUtils() {}

    public static AuthResponse requestAuthToken() {
        Response response = RestAssured
                .given()
                .baseUri(EnvironmentConfig.baseUrl())
                .basePath(EnvironmentConfig.authPath())
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body("{ \"username\": \"user\", \"password\": \"pass\" }")
                .post();

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get token: " + response.getBody().asString());
        }
        String token = response.jsonPath().getString("access_token");
        int expiresIn = response.jsonPath().getInt("expires_in");
        return new AuthResponse(token, expiresIn);
    }

}
