package config;

import utils.PropertyUtils;

import java.util.Properties;

public class EnvironmentConfig {

    private static final Properties props = PropertyUtils.loadProps("test.properties");
    private EnvironmentConfig(){}

    public static String baseUrl() {
        return props.getProperty("base.url");
    }

    public static int timeout(){
        return Integer.parseInt(props.getProperty("timeout", "30"));
    }

    public static String authPath() { return props.getProperty("auth.path"); }
    public static String JSONUrl() {
        return props.getProperty("json.url");
    }

    public static String getAPIKey() {
        return props.getProperty("apiKey");
    }

}
