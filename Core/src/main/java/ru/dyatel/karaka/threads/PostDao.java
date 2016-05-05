package ru.dyatel.karaka.threads;

public interface PostDao {

	void createThread(String boardName, Post post);

	void post(String boardName, Post post);

}
