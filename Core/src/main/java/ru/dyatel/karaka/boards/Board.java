package ru.dyatel.karaka.boards;

public class Board {

	private String name = "Nameless Board";
	private boolean readOnly = false;

	private String postTable = null;
	private String threadTable = null;
	private String attachmentTable = null;

	private String attachmentDir = null;

	private int maxAttachmentsSize = 20480;
	private int bumpLimit = 500;
	private int maxPages = 10;

	private String defaultUsername = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getPostTable() {
		return postTable;
	}

	public void setPostTable(String postTable) {
		this.postTable = postTable;
	}

	public String getThreadTable() {
		return threadTable;
	}

	public void setThreadTable(String threadTable) {
		this.threadTable = threadTable;
	}

	public String getAttachmentTable() {
		return attachmentTable;
	}

	public void setAttachmentTable(String attachmentTable) {
		this.attachmentTable = attachmentTable;
	}

	public String getAttachmentDir() {
		return attachmentDir;
	}

	public void setAttachmentDir(String attachmentDir) {
		this.attachmentDir = attachmentDir;
	}

	public int getMaxAttachmentsSize() {
		return maxAttachmentsSize;
	}

	public void setMaxAttachmentsSize(int maxAttachmentsSize) {
		this.maxAttachmentsSize = maxAttachmentsSize;
	}

	public int getBumpLimit() {
		return bumpLimit;
	}

	public void setBumpLimit(int bumpLimit) {
		this.bumpLimit = bumpLimit;
	}

	public int getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}

	public String getDefaultUsername() {
		return defaultUsername;
	}

	public void setDefaultUsername(String defaultUsername) {
		this.defaultUsername = defaultUsername;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Board board = (Board) o;

		if (readOnly != board.readOnly) return false;
		if (maxAttachmentsSize != board.maxAttachmentsSize) return false;
		if (bumpLimit != board.bumpLimit) return false;
		if (maxPages != board.maxPages) return false;
		if (!name.equals(board.name)) return false;
		if (postTable != null ? !postTable.equals(board.postTable) : board.postTable != null) return false;
		if (threadTable != null ? !threadTable.equals(board.threadTable) : board.threadTable != null) return false;
		if (attachmentTable != null ? !attachmentTable.equals(board.attachmentTable) : board.attachmentTable != null)
			return false;
		return defaultUsername.equals(board.defaultUsername);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + (readOnly ? 1 : 0);
		result = 31 * result + (postTable != null ? postTable.hashCode() : 0);
		result = 31 * result + (threadTable != null ? threadTable.hashCode() : 0);
		result = 31 * result + (attachmentTable != null ? attachmentTable.hashCode() : 0);
		result = 31 * result + maxAttachmentsSize;
		result = 31 * result + bumpLimit;
		result = 31 * result + maxPages;
		result = 31 * result + defaultUsername.hashCode();
		return result;
	}

}
