package com.elephant.seven.validation;

import com.elephant.seven.posts.Post;
import com.elephant.seven.validation.exceptions.EmptyPostException;
import com.elephant.seven.validation.exceptions.TooLongMessageException;
import com.elephant.seven.validation.exceptions.TooLongNameException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.elephant.seven.util.Reference;
import com.elephant.seven.util.StringUtil;

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
