package com.elephant.seven.controllers;

public class ApiRouteNotFoundException extends RuntimeException {

	public ApiRouteNotFoundException() {
	}

	public ApiRouteNotFoundException(String message) {
		super(message);
	}

	public ApiRouteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiRouteNotFoundException(Throwable cause) {
		super(cause);
	}

}
