import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class ConfigProperties {

    public static Properties properties;
    private static String configPath = "./src/main/resources/config.properties";

    public static void initializePropertyFile() throws IOException {
        properties = new Properties();
        FileInputStream file = new FileInputStream(configPath);

        properties.load(file);
    }
}
