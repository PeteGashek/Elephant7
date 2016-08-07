package ru.dyatel.karaka;

import java.util.HashMap;
import java.util.Map;

public class ApiError extends ApiResponse {

	public static class Message {

		public static final String INTERNAL_ERROR = "Internal server error";
		public static final String UNKNOWN_VALIDATION_ERROR = "Supplied data is rejected";
		public static final String NO_SUCH_BOARD = "Requested board doesn't exist";
		public static final String NO_SUCH_THREAD = "Requested thread doesn't exist";
		public static final String EMPTY_MESSAGE = "Message is empty";

	}

	private static Map<String, ApiError> errors = new HashMap<>();

	public static final ApiError INTERNAL_ERROR = create(1, Message.INTERNAL_ERROR);
	public static final ApiError UNKNOWN_VALIDATION_ERROR = create(2, Message.UNKNOWN_VALIDATION_ERROR);
	public static final ApiError NO_SUCH_BOARD = create(3, Message.NO_SUCH_BOARD);
	public static final ApiError NO_SUCH_THREAD = create(4, Message.NO_SUCH_THREAD);
	public static final ApiError EMPTY_MESSAGE = create(5, Message.EMPTY_MESSAGE);

	private static ApiError create(int code, String message) {
		ApiError response = new ApiError(code, message);
		errors.put(message, response);
		return response;
	}

	public static ApiError getByMessage(Object message) {
		if (message instanceof String)
			return errors.get(message);
		return null;
	}

	public ApiError(int code, String description) {
		super(code, description);
	}

}
