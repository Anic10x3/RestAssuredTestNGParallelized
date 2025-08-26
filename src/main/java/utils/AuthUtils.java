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
                .header("x-api-key", EnvironmentConfig.getAPIKey())
                .body("{ \"username\": \"+"+EnvironmentConfig.getUsername()+"+\", \"password\": \""+EnvironmentConfig.getPassword()+"\" }")
                .post();

        if (response.statusCode() != StatusCode.CODE_200.getCode()) {
            throw new RuntimeException("Failed to get token: " + response.getBody().asString());
        }
        String token = response.jsonPath().getString("access_token");
        int expiresIn = response.jsonPath().getInt("expires_in");
        return new AuthResponse(token, expiresIn);
    }

}
