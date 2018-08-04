package com.elephant.seven.data;

import com.elephant.seven.boards.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.elephant.seven.util.Reference;

import java.util.Collection;

import static com.elephant.seven.data.ThreadInfoTable.LAST_POST_ID_COLUMN;

@Component
public class BoardTableManagerImpl implements BoardTableManager {

	@Autowired
	private JdbcTemplate db;

	@Override
	public void prepareTables(Collection<Board> boards) {
		boards.forEach((board) -> {
			db.execute("CREATE TABLE IF NOT EXISTS " + board.getPostTable() + " (" +
					PostTable.POST_ID_COLUMN + " INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
					PostTable.THREAD_ID_COLUMN + " INT UNSIGNED, " +
					PostTable.TIMESTAMP_COLUMN + " DATETIME DEFAULT CURRENT_TIMESTAMP NOT NUll, " +
					PostTable.TYPE_COLUMN + " VARCHAR(8), " +
					PostTable.NAME_COLUMN + " VARCHAR(" + Reference.MAX_NAME_LENGTH + ") CHARACTER SET utf8, " +
					PostTable.MESSAGE_COLUMN + " TEXT CHARACTER SET utf8," +
					PostTable.REPLY_MSGS_IDS + " VARCHAR(255)" +
					")"
			);
			db.execute("CREATE TABLE IF NOT EXISTS " + board.getThreadTable() + " (" +
					ThreadInfoTable.THREAD_ID_COLUMN + " INT UNSIGNED PRIMARY KEY, " +
					LAST_POST_ID_COLUMN + " INT UNSIGNED" +
					")"
			);
		});
	}

}
