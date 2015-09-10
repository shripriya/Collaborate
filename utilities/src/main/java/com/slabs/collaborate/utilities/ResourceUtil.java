package com.slabs.collaborate.utilities;

import java.io.File;
import java.io.IOException;

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

	/**
	 * This method returns the HOME directory based on the value of
	 * {@link RESOURCE_HOME}
	 * 
	 * @param create
	 *            - Boolean value to create home directory if does not exist;
	 * @return {@link File}
	 */
	public static File getHomeDirectory(boolean create) throws CollaborateUtilityException {

		final File home = new File(RESOURCE_HOME);

		if (home.exists()) {
			if (home.isDirectory()) {
				L.info("File path \" {} \" exists", RESOURCE_HOME);
			} else {
				throw new CollaborateUtilityException("File path \"" + RESOURCE_HOME + "\" is not a directory", L);
			}
		} else {
			if (create) {
				L.info("File path \" {} \" does not exist, hence creating the same", RESOURCE_HOME);
				home.mkdirs();
			} else {
				throw new CollaborateUtilityException("File path \"" + RESOURCE_HOME + "\" does not exist", L);
			}
		}

		return home;
	}

	/**
	 * This method returns a directory inside the home directory if it exists.
	 * 
	 * @param directoryName
	 *            - Name of the directory inside {@link RESOURCE_HOME}
	 *            directory.
	 * @param create
	 *            - Boolean value to create directory if does not exist;
	 * @return {@link File}
	 * @throws CollaborateUtilityException
	 */
	public static File getResourceDirectory(String directoryName, boolean create) throws CollaborateUtilityException {

		final File home = getHomeDirectory(create);
		File directory = new File(home, directoryName);
		if (directory.exists()) {
			if (directory.isDirectory()) {
				L.info("File path \" {} \" exists", directory.getPath());
			} else {
				throw new CollaborateUtilityException("File path \"" + directory.getPath() + "\" is not a directory", L);
			}
		} else {
			if (create) {
				L.info("File path \" {} \" does not exist, hence creating the same", directory.getPath());
				directory.mkdirs();
			} else {
				throw new CollaborateUtilityException("File path \"" + directory.getPath() + "\" does not exist", L);
			}
		}
		return directory;
	}

	public static File getResourceFile(String fileName, boolean create) throws CollaborateUtilityException {

		final File home = getHomeDirectory(create);
		File file = new File(home, fileName);
		if (file.exists()) {
			if (file.isFile()) {
				L.info("File \" {} \" exists", file.getPath());
			} else {
				throw new CollaborateUtilityException("File path \"" + file.getPath() + "\" is not a file", L);
			}
		} else {
			if (create) {
				L.info("File path \"{}\"  does not exist, hence creating it", file.getPath());
				try {
					file.createNewFile();
				} catch (IOException e) {
					throw new CollaborateUtilityException("Exception occured while creating file " + file.getPath(), e, L);
				}
			}
		}

		return null;
	}
}
