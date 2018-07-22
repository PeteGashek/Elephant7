package com.elephant.seven.validation.exceptions;

public class TooLongNameException extends ValidationException {

	public TooLongNameException() {
	}

	public TooLongNameException(String message) {
		super(message);
	}

	public TooLongNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public TooLongNameException(Throwable cause) {
		super(cause);
	}

}
