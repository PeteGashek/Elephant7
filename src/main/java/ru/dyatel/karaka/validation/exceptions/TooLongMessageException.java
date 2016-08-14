package ru.dyatel.karaka.validation.exceptions;

public class TooLongMessageException extends ValidationException {

	public TooLongMessageException() {
	}

	public TooLongMessageException(String message) {
		super(message);
	}

	public TooLongMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public TooLongMessageException(Throwable cause) {
		super(cause);
	}

}
