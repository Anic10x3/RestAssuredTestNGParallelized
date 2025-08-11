package utils;

import config.EnvironmentConfig;

import java.util.HashMap;
import java.util.Map;

public class HeaderProvider {

    public static Map<String, String> getDefaultHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("x-api-key", EnvironmentConfig.getAPIKey());
        return headers;
    }
}
