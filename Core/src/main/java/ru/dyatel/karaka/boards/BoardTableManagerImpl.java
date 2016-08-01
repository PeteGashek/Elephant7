package ru.dyatel.karaka.boards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.util.BoardUtil;

import java.util.Map;

import static ru.dyatel.karaka.threads.PostTable.MESSAGE_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.NAME_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.POST_ID_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.THREAD_ID_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.TIMESTAMP_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.TYPE_COLUMN;

@Component
public class BoardTableManagerImpl implements BoardTableManager {

	@Autowired
	private JdbcTemplate db;

	@Override
	public void prepareTables(Map<String, Board> boards) {
		boards.forEach((name, board) -> {
			String postTable = BoardUtil.getPostTable(name, board);
			db.execute("CREATE TABLE IF NOT EXISTS " + postTable + " (" +
					POST_ID_COLUMN + " INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
					THREAD_ID_COLUMN + " INT UNSIGNED, " +
					TIMESTAMP_COLUMN + " TIMESTAMP, " +
					TYPE_COLUMN + " VARCHAR(8), " +
					NAME_COLUMN + " VARCHAR(32), " +
					MESSAGE_COLUMN + " TEXT" +
					")"
			);
		});
	}

}
