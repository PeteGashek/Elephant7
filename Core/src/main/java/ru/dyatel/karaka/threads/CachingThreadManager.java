package ru.dyatel.karaka.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.util.BoardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dyatel.karaka.threads.ThreadInfoTable.LAST_POST_ID_COLUMN;
import static ru.dyatel.karaka.threads.ThreadInfoTable.THREAD_ID_COLUMN;


@Component
public class CachingThreadManager implements ThreadManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	private static final String SELECT_LATEST_THREADS_QUERY = String.format("SELECT %s " +
					"FROM %%s " +
					"ORDER BY %s",
			THREAD_ID_COLUMN, LAST_POST_ID_COLUMN);

	@Autowired
	private JdbcTemplate db;

	@Autowired
	private BoardConfiguration boardConfig;

	private Map<String, List<Long>> cache = new HashMap<>();

	@Override
	public void onNewPost(String boardName, Long threadId) {
		ensureInitialized(boardName);

		List<Long> threads = cache.get(boardName);
		threads.remove(threadId);
		threads.add(threadId);
	}

	@Override
	public List<Long> getLatestThreads(String boardName, int count, int offset) {
		ensureInitialized(boardName);

		List<Long> threads = cache.get(boardName);
		List<Long> latestThreads = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			int index = threads.size() - 1 - offset - i;
			if (index < 0) break;
			latestThreads.add(threads.get(index));
		}
		return latestThreads;
	}

	@Override
	public int getThreadCount(String boardName) {
		ensureInitialized(boardName);
		return cache.get(boardName).size();
	}

	private void ensureInitialized(String boardName) {
		if (!cache.containsKey(boardName)) {
			String table = BoardUtil.getThreadTable(boardName, boardConfig.getBoards().get(boardName));
			cache.put(boardName, db.queryForList(String.format(SELECT_LATEST_THREADS_QUERY, table),
					Long.class));
		}
	}

}
