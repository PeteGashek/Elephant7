package ru.dyatel.karaka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.dyatel.karaka.validation.exceptions.NotValidBoardCodeException;
import ru.dyatel.karaka.validation.exceptions.NotValidThreadException;

@ControllerAdvice
public class ErrorHandler {

	private Logger log = LoggerFactory.getLogger(ErrorHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String fallback(Exception e) {
		log.error("Caught an unhandled exception", e);
		return "error/500";
	}

	@ExceptionHandler({NotValidBoardCodeException.class, NotValidThreadException.class, NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFound() {
		return "error/404";
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public String methodNotAllowed() {
		return "error/405";
	}

}
