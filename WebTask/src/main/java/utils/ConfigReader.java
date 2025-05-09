package utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	 private static Properties properties;

	    static {
	        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
	            properties = new Properties();
	            properties.load(input);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	    public String getBrowser() {
	        return properties.getProperty("browser");
	    }

	    public String getUrl() {
	        return properties.getProperty("url");
	    }
}
