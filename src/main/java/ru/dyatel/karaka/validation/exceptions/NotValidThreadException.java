package ru.dyatel.karaka.validation.exceptions;

public class NotValidThreadException extends ValidationException {

	public NotValidThreadException() {
		super();
	}

	public NotValidThreadException(String message) {
		super(message);
	}

	public NotValidThreadException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidThreadException(Throwable cause) {
		super(cause);
	}

}
