package ru.dyatel.karaka.validation;

public class EmptyPostException extends ValidationException {

	public EmptyPostException() {
	}

	public EmptyPostException(String message) {
		super(message);
	}

	public EmptyPostException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyPostException(Throwable cause) {
		super(cause);
	}

}
