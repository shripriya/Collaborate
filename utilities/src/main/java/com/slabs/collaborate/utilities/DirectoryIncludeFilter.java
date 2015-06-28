package com.slabs.collaborate.utilities;

import java.io.File;
import java.io.FileFilter;

public class DirectoryIncludeFilter implements FileFilter {

	private boolean includeDirectory;

	public DirectoryIncludeFilter(boolean includeDirectory) {

		this.includeDirectory = includeDirectory;
	}

	public boolean accept(File file) {

		if (includeDirectory) {
			return true;
		} else {
			if (!file.isDirectory()) {
				return true;
			} else {
				return false;
			}
		}
	}

}
