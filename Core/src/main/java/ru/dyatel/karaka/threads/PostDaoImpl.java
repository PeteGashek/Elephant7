package ru.dyatel.karaka.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.util.BoardUtil;

import static ru.dyatel.karaka.threads.PostTable.MESSAGE_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.NAME_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.THREAD_ID_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.TYPE_COLUMN;

@Repository
public class PostDaoImpl implements PostDao {

	@Autowired
	private JdbcTemplate db;

	@Autowired
	private BoardConfiguration boardConfig;

	private static final String INSERT_QUERY = String.format("INSERT INTO %%s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
			THREAD_ID_COLUMN, TYPE_COLUMN, NAME_COLUMN, MESSAGE_COLUMN);

	@Override
	public void post(String boardName, Post post) {
		String table = BoardUtil.getPostTable(boardName, boardConfig.getBoards().get(boardName));
		db.update(String.format(INSERT_QUERY, table),
				post.getThreadId(), post.getType().toString(), post.getName(), post.getMessage());
	}

}
