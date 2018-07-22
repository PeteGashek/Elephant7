package com.elephant.seven.data;

import com.elephant.seven.boards.Board;
import com.elephant.seven.posts.Post;

import java.util.List;

public interface PostDao {

	void post(Board board, Post post);

	void deleteThread(Board board, long threadId);

	List<Post> getPosts(Board board, long threadId, int count, int offset);

	List<Post> getPostsById(Board board, List<Long> ids);

}
