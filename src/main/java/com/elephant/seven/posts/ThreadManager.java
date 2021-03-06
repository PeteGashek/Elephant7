package com.elephant.seven.posts;

import com.elephant.seven.boards.Board;

import java.util.List;

public interface ThreadManager {

	void onPostCreate(Board board, Long threadId);

	void onDeleteThread(Board board, Long threadId);

	boolean threadExists(Board board, Long threadId);

	List<Long> getLatestThreads(Board board, int count, int offset);

	int getThreadCount(Board board);

}
