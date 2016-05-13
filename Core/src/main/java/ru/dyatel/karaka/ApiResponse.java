package ru.dyatel.karaka;

import ru.dyatel.karaka.util.ResponseCode;

public class ApiResponse {

	public int code;
	public String message;

	public ApiResponse(ResponseCode code) {
		this(code, null);
	}

	public ApiResponse(ResponseCode code, String message) {
		this.code = code.getCode();
		this.message = message;
	}

}
