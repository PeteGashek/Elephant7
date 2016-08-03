package ru.dyatel.karaka.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.util.BoardUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ru.dyatel.karaka.threads.PostTable.MESSAGE_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.NAME_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.POST_ID_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.THREAD_ID_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.TIMESTAMP_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.TYPE_COLUMN;

@Repository
public class PostDaoImpl implements PostDao {

	@Autowired
	private JdbcTemplate db;

	@Autowired
	private BoardConfiguration boardConfig;

	private static final String INSERT_QUERY = String.format("INSERT INTO %%s (%s, %s, %s, %s) " +
					"VALUES (?, ?, ?, ?)",
			THREAD_ID_COLUMN, TYPE_COLUMN, NAME_COLUMN, MESSAGE_COLUMN);

	private static final String SELECT_LATEST_THREADS_QUERY = String.format("SELECT DISTINCT " +
					"CASE WHEN %1$s = 0 THEN %2$s ELSE %1$s END %1$s " +
					"FROM %%s " +
					"ORDER BY %2$s DESC" +
					"LIMIT ?",
			THREAD_ID_COLUMN, POST_ID_COLUMN);

	private static final String SELECT_POSTS_QUERY = String.format("SELECT %s, %s, %s, %s, %s " +
					"FROM %%s " +
					"WHERE %s = ? OR %1$s = ? " +
					"ORDER BY %1$s",
			POST_ID_COLUMN, TIMESTAMP_COLUMN, TYPE_COLUMN, NAME_COLUMN, MESSAGE_COLUMN, THREAD_ID_COLUMN);

	private static final String SELECT_POSTS_LIMITED_QUERY = SELECT_POSTS_QUERY + " LIMIT ? OFFSET ?";

	@Override
	public void post(String boardName, Post post) {
		String table = BoardUtil.getPostTable(boardName, boardConfig.getBoards().get(boardName));
		db.update(String.format(INSERT_QUERY, table),
				post.getThreadId(), post.getType().toString(), post.getName(), post.getMessage());
	}

	@Override
	public List<Long> getLatestThreads(String boardName, int count) {
		String table = BoardUtil.getPostTable(boardName, boardConfig.getBoards().get(boardName));
		return db.queryForList(String.format(SELECT_LATEST_THREADS_QUERY, table), Long.class, count);
	}

	@Override
	public List<Post> getPosts(String boardName, long threadId, int count, int offset) {
		String table = BoardUtil.getPostTable(boardName, boardConfig.getBoards().get(boardName));
		String defaultUsername = boardConfig.getBoards().get(boardName).getDefaultUsername();
		List<Post> result;
		if (count == 0)
			result = db.query(String.format(SELECT_POSTS_QUERY, table),
					new PostMapper(defaultUsername), threadId, threadId);
		else
			result = db.query(String.format(SELECT_POSTS_LIMITED_QUERY, table),
					new PostMapper(defaultUsername), threadId, threadId, count, offset);
		return result;
	}

	private static class PostMapper implements RowMapper<Post> {

		private String defaultUsername;

		public PostMapper(String defaultUsername) {
			this.defaultUsername = defaultUsername;
		}

		@Override
		public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
			String name = rs.getString(NAME_COLUMN);
			if (!StringUtils.hasText(name)) name = defaultUsername;

			Post post = new Post();
			post.setPostId(rs.getLong(POST_ID_COLUMN));
			post.setTimestamp(rs.getTimestamp(TIMESTAMP_COLUMN).getTime());
			post.setType(PostType.valueOf(rs.getString(TYPE_COLUMN)));
			post.setName(name);
			post.setMessage(rs.getString(MESSAGE_COLUMN));
			return post;
		}

	}

}
