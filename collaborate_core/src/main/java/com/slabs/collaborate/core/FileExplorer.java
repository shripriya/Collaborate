package com.slabs.collaborate.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import com.slabs.collaborate.core.pojo.Directory;
import com.slabs.collaborate.utilities.FileUtil;

public class FileExplorer {

	private static FileExplorer explorer;

	private FileExplorer() {

	}

	public static FileExplorer getInstance() {

		synchronized (FileExplorer.class) {
			if (explorer == null) {
				synchronized (FileExplorer.class) {
					explorer = new FileExplorer();
				}
			}
		}

		return explorer;
	}

	public Files getFilesInDirectory(File directory, boolean includeDirectory) throws FileNotFoundException {

		File[] files = FileUtil.loadFiles(directory, includeDirectory);
		return prepareFileList(files);
	}

	public File[] getFilesArray(File directory, boolean includeDirectory) throws FileNotFoundException {

		return FileUtil.loadFiles(directory, includeDirectory);
	}

	public Files getFilesInDirectory(File directory, String extension) throws FileNotFoundException {

		File[] files = FileUtil.loadFilesWithExtension(directory, true, extension);
		return prepareFileList(files);
	}

	public Files prepareFileList(File[] files) {

		Files list = new Files();
		List<FileInfo> filesList = list.getFile();

		for (File f : files) {
			FileInfo info = new FileInfo();
			Float fileSizeInKB = (float) (f.length() / (1024.0));
			info.setFileName(f.getName());
			info.setFilePath(f.getAbsolutePath());
			info.setFileSize(String.format("%.02f", fileSizeInKB) + " KB");
			info.setIsDirectory(f.isDirectory());

			filesList.add(info);
		}
		return list;
	}

	public List<Directory> prepareDirectoryTree(File[] files) throws FileNotFoundException {

		List<Directory> list = new LinkedList<Directory>();
		for (File f : files) {
			if (f.isDirectory()) {
				Directory d = new Directory();
				d.setName(f.getName());
				d.setIconCls("folderIcon");
				d.setLeaf(false);
				d.setPath(f.getAbsolutePath());
				d.setExpanded(false);
				d.getChildren().addAll(prepareDirectoryTree(FileUtil.loadFiles(f, true)));
				list.add(d);
			} else {
				Directory d = new Directory();
				Float fileSizeInKB = (float) (f.length() / (1024.0));
				d.setIconCls("fileIcon");
				d.setName(f.getName());
				d.setPath(f.getAbsolutePath());
				d.setSize(String.format("%.02f", fileSizeInKB) + " KB");
				d.setLeaf(true);
				list.add(d);
			}
		}

		return list;
	}

}
