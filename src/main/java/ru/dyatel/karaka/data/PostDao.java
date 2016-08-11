package ru.dyatel.karaka.data;

import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.posts.Post;

import java.util.List;

public interface PostDao {

	void post(Board board, Post post);

	void deleteThread(Board board, long threadId);

	List<Post> getPosts(Board board, long threadId, int count, int offset);

	List<Post> getPostsById(Board board, List<Long> ids);

}
