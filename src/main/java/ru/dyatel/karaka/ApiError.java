package ru.dyatel.karaka;

import ru.dyatel.karaka.util.Reference;

public class ApiError extends ApiResponse {

	public static final ApiError INTERNAL_ERROR = new ApiError(1, "Internal server error");
	public static final ApiError NOT_FOUND = new ApiError(2, "Not found");
	public static final ApiError UNKNOWN_VALIDATION_ERROR = new ApiError(3, "Supplied data is rejected");
	public static final ApiError NO_SUCH_BOARD = new ApiError(4, "Requested board doesn't exist");
	public static final ApiError NO_SUCH_THREAD = new ApiError(5, "Requested thread doesn't exist");
	public static final ApiError TOO_LONG_NAME = new ApiError(6, "Name is too long: " + Reference.MAX_NAME_LENGTH + " chars max");
	public static final ApiError EMPTY_POST = new ApiError(7, "The post is empty");

	public ApiError(int code, String description) {
		super(code, description);
	}

}
