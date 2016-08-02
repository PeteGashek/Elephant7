package ru.dyatel.karaka.boards;

import ru.dyatel.karaka.ApiError;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BoardCodeValidator.class)
public @interface BoardCode {

	String message() default ApiError.Message.NO_SUCH_BOARD;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
