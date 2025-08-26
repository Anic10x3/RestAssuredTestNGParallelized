package config.client;

import ch.qos.logback.core.subst.Token;
import config.EnvironmentConfig;
import core.RequestSpecManager;
import core.TokenManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.AuthResponse;
import utils.ExtentRestAssuredFilter;

import java.lang.invoke.SwitchPoint;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.head;

public class ApiClient {

    public static Response sendRequest(String BASE_URL, String endpoint, String method, Object body, Map<String, String>
            headers, Map<String, String> queryParams, boolean requiresAuth) {
        var request = given().spec(RequestSpecManager.getRequestSpec(BASE_URL)).contentType(ContentType.JSON)
                    .filter(new ExtentRestAssuredFilter());
        if (requiresAuth) {
            String token = TokenManager.getToken();
            request.header("Authorization", "Bearer " + token);
        }
        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }
        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }
        if (body != null) {
            request.body(body);
        }

        Response response;
        switch (method.toUpperCase()) {
            case "GET":
                response = request.get(BASE_URL + endpoint);
                break;
            case "POST":
                response = request.post(BASE_URL + endpoint);
                break;
            case "PUT":
                response = request.put(BASE_URL + endpoint);
                break;
            case "DELETE":
                response = request.delete(BASE_URL + endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP Method " + method);
        }
        return response;
    }

    public static Response sendRequest(String baseUrl, String endpoint, String method) {
        return sendRequest(baseUrl,endpoint, method, null, null, null, false);
    }

    public static Response sendRequest(String baseUrl,String endpoint, String method, Object body) {
        return sendRequest(baseUrl, endpoint, method, body, null, null, false);
    }

    public static Response sendRequest(String baseUrl,String endpoint, String method, Map<String, String> queryParams) {
        return sendRequest(baseUrl, endpoint, method, null, null, queryParams, false);
    }

    // 4. With headers only
    public static Response sendRequestWithHeaders(String baseUrl, String endpoint, String method, Map<String, String> headers) {
        return sendRequest(baseUrl,endpoint, method, null, headers, null, false);
    }

    // 5. With headers + query params
    public static Response sendRequest(String baseUrl, String endpoint, String method, Map<String, String> headers, Map<String, String> queryParams) {
        return sendRequest(baseUrl, endpoint, method, null, headers, queryParams, false);
    }

    // 6. Authenticated request with body
    public static Response sendAuthenticatedRequest(String baseUrl, String endpoint, String method, Object body) {
        return sendRequest(baseUrl,endpoint, method, body, null, null, true);
    }

    // 7. Authenticated request with query params
    public static Response sendAuthenticatedRequest(String baseUrl, String endpoint, String method, Map<String, String> queryParams) {
        return sendRequest(baseUrl,endpoint, method, null, null, queryParams, true);
    }

    // 8. Authenticated request with headers + body
    public static Response sendAuthenticatedRequest(String baseUrl,String endpoint, String method, Object body, Map<String, String> headers) {
        return sendRequest(baseUrl,endpoint, method, body, headers, null, true);
    }

}
