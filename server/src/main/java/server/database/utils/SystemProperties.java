package server.database.utils;

import java.io.IOException;
import java.util.Properties;

public class SystemProperties {
    public static String getProperty(String property) {
        Properties properties = new Properties();
        try {
            properties.load(SystemProperties.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException Occurred";
        }

        return properties.getProperty(property);
    }
}
