package ru.dyatel.karaka.threads;

import java.beans.PropertyEditorSupport;

public class PostTypeEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(PostType.valueOf(text.toUpperCase()));
		} catch (IllegalArgumentException e) {
			setValue(PostType.NORMAL);
		}
	}

}
