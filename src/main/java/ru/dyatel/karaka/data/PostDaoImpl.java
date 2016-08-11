package ru.dyatel.karaka.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.posts.Post;
import ru.dyatel.karaka.posts.PostType;
import ru.dyatel.karaka.posts.ThreadManager;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dyatel.karaka.data.PostTable.MESSAGE_COLUMN;
import static ru.dyatel.karaka.data.PostTable.NAME_COLUMN;
import static ru.dyatel.karaka.data.PostTable.POST_ID_COLUMN;
import static ru.dyatel.karaka.data.PostTable.TIMESTAMP_COLUMN;
import static ru.dyatel.karaka.data.PostTable.TYPE_COLUMN;
import static ru.dyatel.karaka.data.ThreadInfoTable.LAST_POST_ID_COLUMN;

@Repository
public class PostDaoImpl implements PostDao {

	@Autowired
	private JdbcTemplate db;
	@Autowired
	private NamedParameterJdbcTemplate namedDb;

	@Autowired
	private ThreadManager threadManager;

	private static final String DELETE_THREAD_QUERY = String.format("DELETE FROM %%s " +
					"WHERE %s = ? OR %s = ?",
			POST_ID_COLUMN, PostTable.THREAD_ID_COLUMN);

	private static final String DELETE_THREAD_INFO_QUERY = String.format("DELETE FROM %%s " +
					"WHERE %s = ?",
			ThreadInfoTable.THREAD_ID_COLUMN);

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
	@Transactional
	public void post(Board board, Post post) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(db)
				.withTableName(board.getPostTable())
				.usingGeneratedKeyColumns(POST_ID_COLUMN);
		Map<String, Object> fields = new HashMap<>();
		fields.put(PostTable.THREAD_ID_COLUMN, post.getThreadId());
		fields.put(TYPE_COLUMN, post.getType());
		fields.put(NAME_COLUMN, post.getName());
		fields.put(MESSAGE_COLUMN, post.getMessage());
		post.setPostId(insert.executeAndReturnKey(fields).longValue());

		if (PostType.SAGE.equals(post.getType())) return;

		String sql;
		if (post.getThreadId() == 0) {
			post.setThreadId(post.getPostId());
			sql = INSERT_THREAD_INFO_QUERY;
		}
		else sql = UPDATE_LAST_POST_IN_THREAD_QUERY;

		db.update(String.format(sql, board.getThreadTable()),
				post.getPostId(), post.getThreadId());

		threadManager.onPostCreate(board, post.getThreadId());
	}

	@Override
	@Transactional
	public void deleteThread(Board board, long threadId) {
		db.update(String.format(DELETE_THREAD_QUERY, board.getPostTable()),
				threadId, threadId);
		db.update(String.format(DELETE_THREAD_INFO_QUERY, board.getThreadTable()),
				threadId);

		threadManager.onDeleteThread(board, threadId);
	}

	@Override
	public List<Post> getPosts(Board board, long threadId, int count, int offset) {
		List<Post> result;
		if (count == 0)
			result = db.query(String.format(SELECT_POSTS_QUERY, board.getPostTable()),
					new PostMapper(board.getDefaultUsername()), threadId, threadId);
		else
			result = db.query(String.format(SELECT_POSTS_LIMITED_QUERY, board.getPostTable()),
					new PostMapper(board.getDefaultUsername()), threadId, threadId, count, offset);
		return result;
	}

	@Override
	public List<Post> getPostsById(Board board, List<Long> ids) {
		if (ids.size() == 0) return Collections.emptyList();
		return namedDb.query(String.format(SELECT_POSTS_BY_ID_QUERY, board.getPostTable()),
				new MapSqlParameterSource("ids", ids),
				new PostMapper(board.getDefaultUsername()));
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
