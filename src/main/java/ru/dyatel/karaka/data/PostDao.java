package ru.dyatel.karaka.data;

import ru.dyatel.karaka.posts.Post;

import java.util.List;

public interface PostDao {

	void post(String boardName, Post post);

	void deleteThread(String boardName, long threadId);

	List<Post> getPosts(String boardName, long threadId, int count, int offset);

	List<Post> getPostsById(String boardName, List<Long> ids);

}
