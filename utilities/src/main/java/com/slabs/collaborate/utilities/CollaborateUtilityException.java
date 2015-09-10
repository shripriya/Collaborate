package com.slabs.collaborate.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollaborateUtilityException extends Exception {

	private static final long serialVersionUID = -8959143264768014701L;

	public CollaborateUtilityException() {

	}

	public CollaborateUtilityException(String message) {

		super(message);
	}

	public CollaborateUtilityException(String message, Logger L) {

		super(message);
		L.error(message);

	}

	public CollaborateUtilityException(Throwable cause) {

		super(cause);

	}

	public CollaborateUtilityException(String message, Throwable cause) {

		super(message, cause);

	}

	public CollaborateUtilityException(String message, Throwable cause, Logger L) {

		super(message, cause);
		L.error(message + ", " + cause.getMessage());

	}

	public CollaborateUtilityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

		super(message, cause, enableSuppression, writableStackTrace);

	}

	public CollaborateUtilityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Logger L) {

		super(message, cause, enableSuppression, writableStackTrace);
		L.error(message);

	}

}
