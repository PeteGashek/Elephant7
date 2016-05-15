package ru.dyatel.karaka;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

	public static class Message {

		public static final String OK = "OK";

		public static final String INTERNAL_ERROR = "Internal server error";
		public static final String UNKNOWN_VALIDATION_ERROR = "Supplied data is rejected";
		public static final String NO_SUCH_BOARD = "Requested board doesn't exist";
		public static final String EMPTY_MESSAGE = "Message is empty";

	}

	private static Map<String, ApiResponse> responses = new HashMap<>();

	public static final ApiResponse OK = create(0, Message.OK);

	public static final ApiResponse INTERNAL_ERROR = create(1, Message.INTERNAL_ERROR);
	public static final ApiResponse UNKNOWN_VALIDATION_ERROR = create(2, Message.UNKNOWN_VALIDATION_ERROR);
	public static final ApiResponse NO_SUCH_BOARD = create(3, Message.NO_SUCH_BOARD);
	public static final ApiResponse EMPTY_MESSAGE = create(4, Message.EMPTY_MESSAGE);

	private static ApiResponse create(int code, String message) {
		ApiResponse response = new ApiResponse(code, message);
		responses.put(message, response);
		return response;
	}

	public static ApiResponse getByMessage(String message) {
		return responses.get(message);
	}

	public int code;
	public String message;

	public ApiResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
