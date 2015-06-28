package com.slabs.collaborate.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollaborateUtilityException extends Exception {

	private static final Logger L = LoggerFactory.getLogger(CollaborateUtilityException.class);

	public CollaborateUtilityException() {

	}

	public CollaborateUtilityException(String message, boolean logMessage) {

		super(message);
		if (logMessage) {
			L.error(message);
		}

	}

	public CollaborateUtilityException(Throwable cause) {

		super(cause);

	}

	public CollaborateUtilityException(String message, Throwable cause, boolean logMessage) {

		super(message, cause);
		if (logMessage) {
			L.error(message + ", " + cause.getMessage());
		}

	}

	public CollaborateUtilityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, boolean logMessage) {

		super(message, cause, enableSuppression, writableStackTrace);
		if (logMessage) {
			L.error(message);
		}

	}

}
