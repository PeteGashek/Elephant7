package ru.dyatel.karaka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

	private Log log = LogFactoryImpl.getLog(ErrorHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiResponse fallback(Exception e) {
		log.error("Caught an unhandled exception", e);
		return ApiResponse.INTERNAL_ERROR;
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse bindingFailure(BindException e) {
		for (ObjectError error : e.getAllErrors()) {
			ApiResponse response = ApiResponse.getByMessage(error.getDefaultMessage());
			if (response != null) return response;
		}
		return ApiResponse.UNKNOWN_VALIDATION_ERROR;
	}

}
