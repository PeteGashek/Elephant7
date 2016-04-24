package ru.dyatel.karaka.boards;

import java.util.Map;

public class BoardUtil {

	private static final String POST_TABLE_PREFIX = "posts_";
	private static final String ATTACHMENT_TABLE_PREFIX = "attachments_";

	public static String getPostTable(Map<String, Board> boards, String board) {
		Board b = boards.get(board);
		String table = b.getPostTable();
		if (table != null) return table;
		return POST_TABLE_PREFIX + board;
	}

	public static String getAttachmentTable(Map<String, Board> boards, String board) {
		Board b = boards.get(board);
		String table = b.getAttachmentTable();
		if (table != null) return table;
		return ATTACHMENT_TABLE_PREFIX + board;
	}

}
