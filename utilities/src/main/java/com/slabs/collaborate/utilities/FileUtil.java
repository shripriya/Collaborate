package com.slabs.collaborate.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shyam
 */
public class FileUtil {

	private static final Logger L = LoggerFactory.getLogger(FileUtil.class);

	public static boolean isDirectoryAvailable(String directoryPath, boolean... create) throws FileNotFoundException {

		File f = new File(directoryPath);
		if (f.exists() && !f.isDirectory()) {
			throw new FileNotFoundException(directoryPath + " is not a directory");
		} else {

			if (f.exists() && f.isDirectory()) {
				L.info(directoryPath + " exists...");
				return true;
			} else if (!f.exists() && create.length == 1 && create[0]) {
				L.info(directoryPath + " does not exist.Creating the directory....");
				f.mkdir();
				return true;
			} else if (create.length == 0) {
				if (!f.exists()) {
					L.info(directoryPath + " does not exist");
					return false;
				}
			}
		}
		return true;
	}

	public static File[] loadFiles(File directory) throws FileNotFoundException {

		return loadFiles(directory, true);

	}

	public static File[] loadFiles(File directory, boolean includeDirectory) throws FileNotFoundException {

		if (directory.exists() && directory.isDirectory()) {
			return directory.listFiles(new DirectoryIncludeFilter(includeDirectory));
		}

		throw new FileNotFoundException(directory.getPath() + "is not a directory");
	}

	/***
	 *
	 * This method loads the files under the specified directory with the
	 * specified extension and returns an array of <code>File</code> objects
	 *
	 * @param directory
	 *            - Directory from which the files to be loaded
	 * @param extension
	 *            - Extension of the file inside the directory
	 * @return - Array of file objects.
	 * @exception - <code>{@link FileNotFoundException}</code> if the specified
	 *            parameter "directory" is not a directory
	 */
	public static File[] loadFilesWithExtension(File directory, boolean includeExtension, String... extension) throws FileNotFoundException {

		if (directory.exists() && directory.isDirectory()) {
			if (extension != null && extension.length > 0) {
				return directory.listFiles(new FileExtensionIncludeFilter(includeExtension, extension));
			} else {
				return directory.listFiles();
			}
		}
		throw new FileNotFoundException(directory.getPath() + "is not a directory");
	}

	public static File loadFile(File directory, String fileName) throws FileNotFoundException {

		if (directory.exists() && directory.isDirectory()) {
			File[] files = loadFilesWithExtension(directory, true);
			for (File file : files) {
				if (file.getName().equals(fileName)) {
					return file;
				}
			}
		}
		throw new FileNotFoundException("File " + fileName + " not found");
	}

	/***
	 * This method returns a file object creating a file under the specified
	 * directory if file does not exist.
	 *
	 * @param directory
	 *            - Directory in which the file is to be created.
	 * @param fileName
	 *            - File name.
	 * @return File object.
	 * @see <code>File</code>
	 */
	public static File createFile(File directory, String fileName) throws IOException {

		File f = new File(directory, fileName);
		if (!f.exists()) {
			f.createNewFile();
		}
		return f;
	}

	public static boolean createDirectory(File directory, String directoryName) throws IOException {

		File f = new File(directory, directoryName);
		if (!f.exists()) {
			return f.mkdir();
		} else {
			return false;
		}

	}

	public static boolean deleteFile(File file) throws IOException {

		if (file.exists()) {
			if (file.isDirectory()) {
				if (file.listFiles().length == 0) {
					return file.delete();
				} else if (file.listFiles().length > 0) {
					File[] children = file.listFiles();
					for (File child : children) {
						deleteFile(child);
					}
					return file.delete();
				}
			} else {
				return file.delete();
			}
		}

		return false;
	}

	public static boolean moveFile(File srcFile, File destFile) throws IOException {

		if (srcFile.exists() && destFile.exists()) {
			if (srcFile.isDirectory() && destFile.isDirectory()) {
				FileUtils.moveDirectoryToDirectory(srcFile, destFile, false);
			} else if (srcFile.isFile() && destFile.isDirectory()) {
				FileUtils.moveFileToDirectory(srcFile, destFile, false);
			}
			return true;
		}

		return false;
	}

	/***
	 * This method takes a directory path and creates the directories if it does
	 * not exist and creates the specified file under the directory.
	 *
	 * @param directoryPath
	 *            - Directory path in which the file is to be created
	 * @param fileName
	 *            - File name;
	 * @return File Object
	 * @throws IOException
	 */
	public static File createFile(String directoryPath, String fileName) throws IOException {

		File dir = new File(directoryPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(dir, fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public static String readFile(File file) {

		BufferedReader bf = null;
		if (file.exists()) {
			if (file.isFile()) {
				DataInputStream ds = null;
				try {
					ds = new DataInputStream(new FileInputStream(file));
					bf = new BufferedReader(new InputStreamReader(ds));
					String fileString = "", temp = "";

					while ((temp = bf.readLine()) != null) {
						fileString = fileString + temp;
					}
					return fileString;
				} catch (IOException ex) {
					L.error("Error reading file {}, exception {}", file.getName(), ex);
				} finally {
					try {
						ds.close();
						bf.close();
					} catch (IOException ex) {
						L.error("Error closing stream {}", ex);
					}
				}
			}
		}
		return "";
	}
}
