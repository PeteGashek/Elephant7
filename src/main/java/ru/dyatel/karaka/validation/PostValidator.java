package ru.dyatel.karaka.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.dyatel.karaka.posts.Post;
import ru.dyatel.karaka.validation.exceptions.EmptyPostException;

@Component
public class PostValidator {

	public void validate(Post post) {
		if (!StringUtils.hasText(post.getMessage()))
			throw new EmptyPostException();
	}

}
