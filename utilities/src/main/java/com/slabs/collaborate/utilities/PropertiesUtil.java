package com.slabs.collaborate.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shyam
 */
public class PropertiesUtil {

	private static final Logger L = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

	/**
	 * * This method loads the properties from the files under the specified
	 * directory
	 *
	 *
	 * @param directory
	 *            - Directory from where the property files should be loaded
	 * @param extension
	 *            - Extension of the property file. <br>
	 * <br>
	 *            <b>Property file can be created with any unreserved extension.
	 *            Create property file with contents like key=value.</b> <br>
	 * <br>
	 *            For example,<br>
	 * <br>
	 *            key1=value1<br>
	 *            key2=value2<br>
	 *            key3=value3<br>
	 *            key4=value4<br>
	 *            . <br>
	 *            . <br>
	 *            . <br>
	 */
	public static void loadProperties(File directory, String extension) {

		if (directory.isDirectory()) {
			try {
				File[] loadFiles = FileUtil.loadFilesWithExtension(directory, true, extension);
				for (File f : loadFiles) {
					L.info("Reading property file {} ....", f.getName());
					getProperties(f);
				}
			} catch (FileNotFoundException ex) {
				L.error("Error loading properties {}", ex);
			}
		}
	}

	public static Properties getPropertiesFromClassPath(String fileName) throws IOException {

		Properties p = new Properties();
		InputStream is = LookupUtil.class.getClassLoader().getResourceAsStream(fileName);
		p.load(is);
		return p;

	}

	/**
	 * *
	 *
	 * This method returns a <code>{@link Properties}</code> object for the
	 * corresponding property file and also populates the static properties map.
	 *
	 * @param file
	 *            - Property file from where to load the properties.
	 * @return Properties
	 * @see Properties
	 *
	 */
	public static Properties getProperties(File file) {

		Properties p = new Properties();
		
		try {
			p.load(new FileInputStream(file));			
			loadPropertiesMap(file, p);
			return p;
		} catch (IOException ex) {
			L.error("Error loading properties {}", ex);
		}
		return p;
	}

	/**
	 * *
	 *
	 * @return a map of Properties for the corresponding property file provided
	 *         it is already populated.
	 */
	public static Map<String, Properties> getPropertiesMap() {

		return propertiesMap;
	}

	private static void loadPropertiesMap(File file, Properties p) {

		propertiesMap.put(file.getName(), p);
	}

}
