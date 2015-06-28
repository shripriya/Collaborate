package com.slabs.collaborate.utilities;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shyam
 */
public class ResourceUtil {

	private static final Logger L = LoggerFactory.getLogger(ResourceUtil.class);

	private static String RESOURCE_HOME;

	public static void initialize(String collaborateEnvironment) {

		RESOURCE_HOME = System.getenv(collaborateEnvironment);

	}

	public static File getHomeDirectory() {

		final File home = new File(RESOURCE_HOME);
		if (home.exists()) {
			L.info("File path \" {} \" exists", RESOURCE_HOME);
			return home;
		} else {
			L.info("File path \" {} \" does not exist, hence creating the same",
					RESOURCE_HOME);
			home.mkdirs();
			return home;
		}
	}

	public static File getConfigDirectory() {

		final File home = getHomeDirectory();

		File config = new File(home, "config");
		if (config.exists()) {
			L.info("File path \" {} \" exists", config.getPath());
			return config;
		} else {
			L.info("File path \" {} \" does not exist, hence creating the same",
					config.getPath());
			config.mkdirs();
			return config;
		}
	}

	public static File getPropertiesDirectory() {

		final File home = getHomeDirectory();

		File properties = new File(home, "properties");
		if (properties.exists()) {
			L.info("File path \" {} \" exists", properties.getPath());
			return properties;
		} else {
			L.info("File path \" {} \" does not exist, hence creating the same",
					properties.getPath());
			properties.mkdirs();
			return properties;
		}
	}
}
