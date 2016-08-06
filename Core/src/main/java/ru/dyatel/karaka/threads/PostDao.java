package ru.dyatel.karaka.threads;

import java.util.List;

public interface PostDao {

	void post(String boardName, Post post);

	List<Post> getPosts(String boardName, long threadId, int count, int offset);

	List<Post> getPostsById(String boardName, List<Long> ids);

}
