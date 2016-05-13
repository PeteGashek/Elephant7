package ru.dyatel.karaka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.dyatel.karaka.util.ResponseCode;

@ControllerAdvice
public class ErrorHandler {

	private Log log = LogFactoryImpl.getLog(ErrorHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiResponse fallback(Exception e) {
		log.error("Caught an unhandled exception", e);
		return new ApiResponse(ResponseCode.INTERNAL_ERROR, "Internal server error");
	}

}
