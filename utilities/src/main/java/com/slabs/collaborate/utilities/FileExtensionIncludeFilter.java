/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slabs.collaborate.utilities;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author shyam
 */
public class FileExtensionIncludeFilter implements FilenameFilter {

	private List extensions;

	private boolean isInclude;

	public FileExtensionIncludeFilter(boolean isInclude, String... extensions) {

		this.extensions = Arrays.asList(extensions);
		this.isInclude = isInclude;
	}

	public boolean accept(File dir, String name) {

		String fileExtension = name.substring(name.lastIndexOf("."));

		if (isInclude) {
			return extensions.contains(fileExtension);
		} else {
			return !extensions.contains(fileExtension);
		}

	}
}
