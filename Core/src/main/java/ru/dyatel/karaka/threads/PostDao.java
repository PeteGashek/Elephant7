package ru.dyatel.karaka.threads;

import java.util.List;

public interface PostDao {

	void post(String boardName, Post post);

	List<Long> getLatestThreads(String boardName, int count);

	List<Post> getPosts(String boardName, long threadId, int count, int offset);

}
