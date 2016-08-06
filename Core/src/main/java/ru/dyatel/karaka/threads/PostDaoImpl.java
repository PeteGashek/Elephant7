package ru.dyatel.karaka.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.util.BoardUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dyatel.karaka.threads.PostTable.MESSAGE_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.NAME_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.POST_ID_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.TIMESTAMP_COLUMN;
import static ru.dyatel.karaka.threads.PostTable.TYPE_COLUMN;
import static ru.dyatel.karaka.threads.ThreadInfoTable.LAST_POST_ID_COLUMN;

@Repository
public class PostDaoImpl implements PostDao {

	@Autowired
	private JdbcTemplate db;
	@Autowired
	private NamedParameterJdbcTemplate namedDb;

	@Autowired
	private BoardConfiguration boardConfig;

	@Autowired
	private ThreadManager threadManager;

	private static final String INSERT_THREAD_INFO_QUERY = String.format("INSERT INTO %%s(%s, %s)" +
					"VALUES (?, ?)",
			LAST_POST_ID_COLUMN, ThreadInfoTable.THREAD_ID_COLUMN);

	private static final String UPDATE_LAST_POST_IN_THREAD_QUERY = String.format("UPDATE %%s " +
					"SET %s = ? " +
					"WHERE %s = ?",
			LAST_POST_ID_COLUMN, ThreadInfoTable.THREAD_ID_COLUMN);

	private static final String SELECT_POSTS_QUERY = String.format("SELECT %s, %s, %s, %s, %s " +
					"FROM %%s " +
					"WHERE %s = ? OR %1$s = ? " +
					"ORDER BY %1$s",
			POST_ID_COLUMN, TIMESTAMP_COLUMN, TYPE_COLUMN, NAME_COLUMN, MESSAGE_COLUMN, PostTable.THREAD_ID_COLUMN);

	private static final String SELECT_POSTS_LIMITED_QUERY = SELECT_POSTS_QUERY + " LIMIT ? OFFSET ?";

	private static final String SELECT_POSTS_BY_ID_QUERY = String.format("SELECT %s, %s, %s, %s, %s, " +
					"CASE WHEN %6$s = 0 THEN %1$s ELSE %6$s END %6s " +
					"FROM %%s " +
					"WHERE %1$s IN (:ids)",
			POST_ID_COLUMN, TIMESTAMP_COLUMN, TYPE_COLUMN, NAME_COLUMN, MESSAGE_COLUMN, PostTable.THREAD_ID_COLUMN);

	@Override
	public void post(String boardName, Post post) {
		boolean isThread = post.getThreadId() == 0;
		Board board = boardConfig.getBoards().get(boardName);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(db)
				.withTableName(BoardUtil.getPostTable(boardName, board))
				.usingGeneratedKeyColumns(POST_ID_COLUMN);
		Map<String, Object> fields = new HashMap<>();
		fields.put(PostTable.THREAD_ID_COLUMN, post.getThreadId());
		fields.put(TYPE_COLUMN, post.getType());
		fields.put(NAME_COLUMN, post.getName());
		fields.put(MESSAGE_COLUMN, post.getMessage());

		post.setPostId(insert.executeAndReturnKey(fields).longValue());
		if (isThread) post.setThreadId(post.getPostId());

		String sql;
		if (isThread) sql = INSERT_THREAD_INFO_QUERY;
		else sql = UPDATE_LAST_POST_IN_THREAD_QUERY;

		db.update(String.format(sql, BoardUtil.getThreadTable(boardName, boardConfig.getBoards().get(boardName))),
				post.getPostId(), post.getThreadId());

		threadManager.onNewPost(boardName, post.getThreadId());
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

	@Override
	public List<Post> getPostsById(String boardName, List<Long> ids) {
		if (ids.size() == 0) return Collections.emptyList();
		String table = BoardUtil.getPostTable(boardName, boardConfig.getBoards().get(boardName));
		return namedDb.query(String.format(SELECT_POSTS_BY_ID_QUERY, table),
				new MapSqlParameterSource("ids", ids),
				new PostMapper(boardConfig.getBoards().get(boardName).getDefaultUsername()));
	}

	private static class PostMapper implements RowMapper<Post> {

		private String defaultUsername;

		public PostMapper(String defaultUsername) {
			this.defaultUsername = defaultUsername;
		}

		@Override
		public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long threadId = null;
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (metaData.getColumnName(i).equals(PostTable.THREAD_ID_COLUMN)) {
					threadId = rs.getLong(PostTable.THREAD_ID_COLUMN);
					break;
				}
			}

			String name = rs.getString(NAME_COLUMN);
			if (!StringUtils.hasText(name)) name = defaultUsername;

			Post post = new Post();
			post.setPostId(rs.getLong(POST_ID_COLUMN));
			post.setThreadId(threadId);
			post.setTimestamp(rs.getTimestamp(TIMESTAMP_COLUMN).getTime());
			post.setType(PostType.valueOf(rs.getString(TYPE_COLUMN)));
			post.setName(name);
			post.setMessage(rs.getString(MESSAGE_COLUMN));
			return post;
		}

	}

}
