package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class EnvPropertiesReader {
	public Properties properties;

	public void loadPropertiesFile() {

		properties = new Properties();

		File propFile = new File(System.getProperty("user.dir") + "/src/test/resources/env.properties");
		try {
			FileInputStream fis = new FileInputStream(propFile);
			properties.load(fis);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
