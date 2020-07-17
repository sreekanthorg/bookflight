package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

	public static String get(String keyName)  {
		Properties properties = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream
					(System.getProperty("user.dir")+"/src/test/java/resources/config.properties");
			properties.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return properties.getProperty(keyName);
	}
}
