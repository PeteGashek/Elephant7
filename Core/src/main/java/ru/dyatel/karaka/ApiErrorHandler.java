package ru.dyatel.karaka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.dyatel.karaka.validation.EmptyPostException;
import ru.dyatel.karaka.validation.NotValidBoardCodeException;
import ru.dyatel.karaka.validation.NotValidThreadException;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ApiErrorHandler {

	private Log log = LogFactoryImpl.getLog(ApiErrorHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse fallback(Exception e) {
		log.error("Caught an unhandled exception", e);
		return ApiError.INTERNAL_ERROR;
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

	@ExceptionHandler(EmptyPostException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse emptyPost() {
		return ApiError.EMPTY_POST;
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse bindingFailure(BindException e) {
		return ApiError.UNKNOWN_VALIDATION_ERROR;
	}

}
