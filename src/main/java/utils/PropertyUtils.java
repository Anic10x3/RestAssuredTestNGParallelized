package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
    public static Properties loadProps(String name){
        try(InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name)){
            Properties p = new Properties();
            if(inputStream!=null){
                p.load(inputStream);
            }
            return p;
        } catch (IOException e){
            throw new RuntimeException("Failed to load properties: " + name, e);
        }
    }
}
