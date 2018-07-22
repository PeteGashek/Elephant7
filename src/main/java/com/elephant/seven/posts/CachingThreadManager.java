package com.elephant.seven.posts;

import com.elephant.seven.boards.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.elephant.seven.data.ThreadInfoTable.LAST_POST_ID_COLUMN;
import static com.elephant.seven.data.ThreadInfoTable.THREAD_ID_COLUMN;

@Component
public class CachingThreadManager implements ThreadManager {

	private static final String SELECT_LATEST_THREADS_QUERY = String.format("SELECT %s " +
					"FROM %%s " +
					"ORDER BY %s",
			THREAD_ID_COLUMN, LAST_POST_ID_COLUMN);

	@Autowired
	private JdbcTemplate db;

	private Map<Board, List<Long>> cache = new HashMap<>();

	@Override
	public void onPostCreate(Board board, Long threadId) {
		ensureInitialized(board);

		List<Long> threads = cache.get(board);
		threads.remove(threadId);
		threads.add(threadId);
	}

	@Override
	public void onDeleteThread(Board board, Long threadId) {
		ensureInitialized(board);
		cache.get(board).remove(threadId);
	}

	@Override
	public boolean threadExists(Board board, Long threadId) {
		ensureInitialized(board);
		return cache.get(board).contains(threadId);
	}

	@Override
	public List<Long> getLatestThreads(Board board, int count, int offset) {
		ensureInitialized(board);

		List<Long> threads = cache.get(board);
		List<Long> latestThreads = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			int index = threads.size() - 1 - offset - i;
			if (index < 0) break;
			latestThreads.add(threads.get(index));
		}
		return latestThreads;
	}

	@Override
	public int getThreadCount(Board board) {
		ensureInitialized(board);
		return cache.get(board).size();
	}

	private void ensureInitialized(Board board) {
		if (!cache.containsKey(board)) {
			cache.put(board, db.queryForList(String.format(SELECT_LATEST_THREADS_QUERY, board.getThreadTable()),
					Long.class));
		}
	}

}
