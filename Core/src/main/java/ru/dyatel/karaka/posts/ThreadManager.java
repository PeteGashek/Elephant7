package ru.dyatel.karaka.posts;

import java.util.List;

public interface ThreadManager {

	void onPostCreate(String boardName, Long threadId);

	void onDeleteThread(String boardName, Long threadId);

	List<Long> getLatestThreads(String boardName, int count, int offset);

	int getThreadCount(String boardName);

}
