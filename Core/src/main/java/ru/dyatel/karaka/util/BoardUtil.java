package ru.dyatel.karaka.util;

import ru.dyatel.karaka.boards.Board;

import java.nio.file.Path;

public class BoardUtil {

	private static final String POST_TABLE_PREFIX = "posts_";
	private static final String THREAD_TABLE_PREFIX = "threads_";
	private static final String ATTACHMENT_TABLE_PREFIX = "attachments_";

	public static String getPostTable(String boardName, Board board) {
		String table = board.getPostTable();
		if (table != null) return table;
		return POST_TABLE_PREFIX + boardName;
	}

	public static String getThreadTable(String boardName, Board board) {
		String table = board.getThreadTable();
		if (table != null) return table;
		return THREAD_TABLE_PREFIX + boardName;
	}

	public static String getAttachmentTable(String boardName, Board board) {
		String table = board.getAttachmentTable();
		if (table != null) return table;
		return ATTACHMENT_TABLE_PREFIX + boardName;
	}

	public static Path getAttachmentDir(String boardName, Board board, Path workingDir) {
		String dir = board.getAttachmentDir();
		if (dir == null) dir = boardName;
		return workingDir.resolve(dir + "/");
	}

}
