package ru.dyatel.karaka.boards;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BoardCodeValidator implements ConstraintValidator<BoardCode, String> {

	@Autowired
	private BoardConfiguration boardConfig;

	@Override
	public void initialize(BoardCode constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return boardConfig.getBoards().containsKey(value);
	}

}
