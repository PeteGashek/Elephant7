package ru.dyatel.karaka.util;

public enum ResponseCode {

	OK(0), INTERNAL_ERROR(1), NO_SUCH_BOARD(2), EMPTY_MESSAGE(3);

	private int code;

	ResponseCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
