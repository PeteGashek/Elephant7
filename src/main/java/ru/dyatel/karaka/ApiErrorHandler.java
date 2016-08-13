package ru.dyatel.karaka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.dyatel.karaka.controllers.Api;
import ru.dyatel.karaka.controllers.ApiRouteNotFoundException;
import ru.dyatel.karaka.validation.exceptions.EmptyPostException;
import ru.dyatel.karaka.validation.exceptions.NotValidBoardCodeException;
import ru.dyatel.karaka.validation.exceptions.NotValidThreadException;
import ru.dyatel.karaka.validation.exceptions.TooLongNameException;
import ru.dyatel.karaka.validation.exceptions.ValidationException;

@ControllerAdvice(annotations = Api.class)
@ResponseBody
public class ApiErrorHandler {

	private Log log = LogFactoryImpl.getLog(ApiErrorHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse fallback(Exception e) {
		log.error("Caught an unhandled exception", e);
		return ApiError.INTERNAL_ERROR;
	}

	@ExceptionHandler(ApiRouteNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse notFound() {
		return ApiError.NOT_FOUND;
	}

	@ExceptionHandler(NotValidBoardCodeException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse notValidBoardCode() {
		return ApiError.NO_SUCH_BOARD;
	}

	@ExceptionHandler(NotValidThreadException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse notValidThread() {
		return ApiError.NO_SUCH_THREAD;
	}

	@ExceptionHandler(TooLongNameException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse tooLongName() {
		return ApiError.TOO_LONG_NAME;
	}

	@ExceptionHandler(EmptyPostException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse emptyPost() {
		return ApiError.EMPTY_POST;
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse bindingFailure() {
		return ApiError.UNKNOWN_VALIDATION_ERROR;
	}

}
