package ru.dyatel.karaka.util;

public enum ResponseCode {

	OK(0);

	private int code;

	ResponseCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
