package ru.dyatel.karaka.boards;

public class BoardUtil {

	private static final String POST_TABLE_PREFIX = "posts_";
	private static final String ATTACHMENT_TABLE_PREFIX = "attachments_";

	public static String getPostTable(String boardName, Board board) {
		String table = board.getPostTable();
		if (table != null) return table;
		return POST_TABLE_PREFIX + boardName;
	}

	public static String getAttachmentTable(String boardName, Board board) {
		String table = board.getAttachmentTable();
		if (table != null) return table;
		return ATTACHMENT_TABLE_PREFIX + boardName;
	}

}
