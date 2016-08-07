package ru.dyatel.karaka.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.util.BoardUtil;

import java.util.Map;

import static ru.dyatel.karaka.data.PostTable.MESSAGE_COLUMN;
import static ru.dyatel.karaka.data.PostTable.NAME_COLUMN;
import static ru.dyatel.karaka.data.PostTable.POST_ID_COLUMN;
import static ru.dyatel.karaka.data.PostTable.TIMESTAMP_COLUMN;
import static ru.dyatel.karaka.data.PostTable.TYPE_COLUMN;
import static ru.dyatel.karaka.data.ThreadInfoTable.LAST_POST_ID_COLUMN;

@Component
public class BoardTableManagerImpl implements BoardTableManager {

	@Autowired
	private JdbcTemplate db;

	@Override
	public void prepareTables(Map<String, Board> boards) {
		boards.forEach((name, board) -> {
			db.execute("CREATE TABLE IF NOT EXISTS " + BoardUtil.getPostTable(name, board) + " (" +
					POST_ID_COLUMN + " INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
					PostTable.THREAD_ID_COLUMN + " INT UNSIGNED, " +
					TIMESTAMP_COLUMN + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
					TYPE_COLUMN + " VARCHAR(8), " +
					NAME_COLUMN + " VARCHAR(32) CHARACTER SET utf8, " +
					MESSAGE_COLUMN + " TEXT CHARACTER SET utf8" +
					")"
			);
			db.execute("CREATE TABLE IF NOT EXISTS " + BoardUtil.getThreadTable(name, board) + " (" +
					ThreadInfoTable.THREAD_ID_COLUMN + " INT UNSIGNED PRIMARY KEY, " +
					LAST_POST_ID_COLUMN + " INT UNSIGNED" +
					")"
			);
		});
	}

}
