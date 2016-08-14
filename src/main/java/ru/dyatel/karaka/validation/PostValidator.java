package ru.dyatel.karaka.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.dyatel.karaka.posts.Post;
import ru.dyatel.karaka.util.Reference;
import ru.dyatel.karaka.util.StringUtil;
import ru.dyatel.karaka.validation.exceptions.EmptyPostException;
import ru.dyatel.karaka.validation.exceptions.TooLongMessageException;
import ru.dyatel.karaka.validation.exceptions.TooLongNameException;

@Component
public class PostValidator {

	public void validate(Post post) {
		if (post.getName().length() > Reference.MAX_NAME_LENGTH)
			throw new TooLongNameException();
		if (!StringUtils.hasText(post.getMessage()))
			throw new EmptyPostException();
		if (StringUtil.utf8ByteLength(post.getMessage()) > Reference.MAX_MESSAGE_BYTE_LENGTH)
			throw new TooLongMessageException();
	}

}
