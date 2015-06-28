package com.slabs.collaborate.core.entity;

public class SharedFiles {

	private String fileName;

	private String filePath;

	private String sharedBy;

	private String sharedWith;

	private String ownerLName;

	private String ownerFName;

	public SharedFiles() {

		super();

	}

	public SharedFiles(String fileName, String filePath, String sharedBy, String sharedWith, String ownerLName, String ownerFName) {

		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.sharedBy = sharedBy;
		this.sharedWith = sharedWith;
		this.ownerLName = ownerLName;
		this.ownerFName = ownerFName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {

		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {

		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {

		this.filePath = filePath;
	}

	/**
	 * @return the sharedBy
	 */
	public String getSharedBy() {

		return sharedBy;
	}

	/**
	 * @param sharedBy
	 *            the sharedBy to set
	 */
	public void setSharedBy(String sharedBy) {

		this.sharedBy = sharedBy;
	}

	/**
	 * @return the sharedWith
	 */
	public String getSharedWith() {

		return sharedWith;
	}

	/**
	 * @param sharedWith
	 *            the sharedWith to set
	 */
	public void setSharedWith(String sharedWith) {

		this.sharedWith = sharedWith;
	}

	/**
	 * @return the ownerLName
	 */
	public String getOwnerLName() {

		return ownerLName;
	}

	/**
	 * @param ownerLName
	 *            the ownerLName to set
	 */
	public void setOwnerLName(String ownerLName) {

		this.ownerLName = ownerLName;
	}

	/**
	 * @return the ownerFName
	 */
	public String getOwnerFName() {

		return ownerFName;
	}

	/**
	 * @param ownerFName
	 *            the ownerFName to set
	 */
	public void setOwnerFName(String ownerFName) {

		this.ownerFName = ownerFName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "SharedFiles [fileName=" + fileName + ", filePath=" + filePath + ", sharedBy=" + sharedBy + ", sharedWith=" + sharedWith + ", ownerLName=" + ownerLName + ", ownerFName=" + ownerFName
				+ "]";
	}

}
