package ru.dyatel.karaka;

public class ApiError extends ApiResponse {

	public static final ApiError INTERNAL_ERROR = new ApiError(1, "Internal server error");
	public static final ApiError UNKNOWN_VALIDATION_ERROR = new ApiError(2, "Supplied data is rejected");
	public static final ApiError NO_SUCH_BOARD = new ApiError(3, "Requested board doesn't exist");
	public static final ApiError NO_SUCH_THREAD = new ApiError(4, "Requested thread doesn't exist");
	public static final ApiError EMPTY_MESSAGE = new ApiError(5, "Message is empty");

	public ApiError(int code, String description) {
		super(code, description);
	}

}
