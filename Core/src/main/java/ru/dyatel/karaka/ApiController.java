package ru.dyatel.karaka;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dyatel.karaka.util.ResponseCode;

@RestController
@RequestMapping("/api")
public class ApiController {

	private static class ApiResponse {

		public int code;
		public String message;

		public ApiResponse(ResponseCode code) {
			this(code, null);
		}

		public ApiResponse(ResponseCode code, String message) {
			this.code = code.getCode();
			this.message = message;
		}

	}

	@RequestMapping("/test")
	public String test() {
		return "Hello, World!";
	}

}
