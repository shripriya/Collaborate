package com.slabs.collaborate.listener;

import java.io.Serializable;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadListener implements ProgressListener, Serializable {

	private Long percentDone = 0L;

	private float actualDone = 0;

	public void update(long bytesRead, long contentLength, int items) {

		percentDone = (Long) Math.round(100.00 * bytesRead / contentLength);
		actualDone = percentDone.floatValue()/100;

	}

	public long getPercentDone() {

		return percentDone;
	}

	public float getActualDone() {
		return actualDone;
	}

}
