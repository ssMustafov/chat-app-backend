package org.ruse.uni.chat.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

/**
 *
 * @author sinan
 */
@Singleton
public class PropertyFileResolver {

	private static final String PROPERTY_FILE_NAME = "configuration.properties";
	private Map<Object, Object> propertiesMap;

	@PostConstruct
	private void init() {
		Properties properties = new Properties();

		try (InputStream inputStream = new FileInputStream(getPropertyFile())) {
			properties.load(inputStream);
		} catch (IOException e) {
			System.out.println("Unable to load properties file" + e);
		}

		propertiesMap = Collections.unmodifiableMap(properties);
	}

	private static File getPropertyFile() {
		// matches the property name as defined in the system-properties element
		// in WildFly
		return new File(System.getProperty(PROPERTY_FILE_NAME));
	}

	public String getProperty(String key) {
		return propertiesMap.get(key).toString();
	}

}
