package ru.dyatel.karaka;

public class ApiResponse {

	public static final int OK_CODE = 0;

	public static final ApiResponse OK = new ApiResponse("OK");

	protected int code;
	protected Object message;

	public ApiResponse() {
		this(OK_CODE);
	}

	public ApiResponse(int code) {
		this(code, null);
	}

	public ApiResponse(Object message) {
		this(OK_CODE, message);
	}

	public ApiResponse(int code, Object message) {
		this.code = code;
		this.message = message;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}
