package ru.dyatel.karaka.validation;

public class NotValidBoardCodeException extends IllegalArgumentException {

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
