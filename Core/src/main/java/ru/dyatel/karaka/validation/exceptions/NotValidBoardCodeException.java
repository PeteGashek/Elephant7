package ru.dyatel.karaka.validation.exceptions;

public class NotValidBoardCodeException extends ValidationException {

	public NotValidBoardCodeException() {
	}

	public NotValidBoardCodeException(String s) {
		super(s);
	}

	public NotValidBoardCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidBoardCodeException(Throwable cause) {
		super(cause);
	}

}
