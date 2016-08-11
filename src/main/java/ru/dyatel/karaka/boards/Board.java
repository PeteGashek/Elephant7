package ru.dyatel.karaka.boards;

public class Board {

	public static class DefaultConfig {

		public boolean readOnly = false;

		public int bumpLimit = 500;
		public int maxPages = 10;

		public String defaultUsername = "";

	}

	private static final String POST_TABLE_PREFIX = "posts_";
	private static final String THREAD_TABLE_PREFIX = "threads_";

	private transient DefaultConfig defaultConfig;

	private String code;
	private String description = null;
	private Boolean readOnly = null;

	private String postTable = null;
	private String threadTable = null;

	private Integer bumpLimit = null;
	private Integer maxPages = null;

	private String defaultUsername = null;

	public DefaultConfig getDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(DefaultConfig defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description == null ? "/" + code + "/" : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isReadOnly() {
		return readOnly == null ? defaultConfig.readOnly : readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getPostTable() {
		return postTable == null ? POST_TABLE_PREFIX + code : postTable;
	}

	public void setPostTable(String postTable) {
		this.postTable = postTable;
	}

	public String getThreadTable() {
		return threadTable == null ? THREAD_TABLE_PREFIX + code : threadTable;
	}

	public void setThreadTable(String threadTable) {
		this.threadTable = threadTable;
	}

	public int getBumpLimit() {
		return bumpLimit == null ? defaultConfig.bumpLimit : bumpLimit;
	}

	public void setBumpLimit(int bumpLimit) {
		this.bumpLimit = bumpLimit;
	}

	public int getMaxPages() {
		return maxPages == null ? defaultConfig.maxPages : maxPages;
	}

	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}

	public String getDefaultUsername() {
		return defaultUsername == null || defaultUsername.equals("") ? defaultConfig.defaultUsername : defaultUsername;
	}

	public void setDefaultUsername(String defaultUsername) {
		this.defaultUsername = defaultUsername;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Board board = (Board) o;

		if (!code.equals(board.code)) return false;
		if (description != null ? !description.equals(board.description) : board.description != null) return false;
		if (readOnly != null ? !readOnly.equals(board.readOnly) : board.readOnly != null) return false;
		if (postTable != null ? !postTable.equals(board.postTable) : board.postTable != null) return false;
		if (threadTable != null ? !threadTable.equals(board.threadTable) : board.threadTable != null) return false;
		if (bumpLimit != null ? !bumpLimit.equals(board.bumpLimit) : board.bumpLimit != null) return false;
		if (maxPages != null ? !maxPages.equals(board.maxPages) : board.maxPages != null) return false;
		return defaultUsername != null ? defaultUsername.equals(board.defaultUsername) : board.defaultUsername == null;

	}

	@Override
	public int hashCode() {
		int result = code.hashCode();
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (readOnly != null ? readOnly.hashCode() : 0);
		result = 31 * result + (postTable != null ? postTable.hashCode() : 0);
		result = 31 * result + (threadTable != null ? threadTable.hashCode() : 0);
		result = 31 * result + (bumpLimit != null ? bumpLimit.hashCode() : 0);
		result = 31 * result + (maxPages != null ? maxPages.hashCode() : 0);
		result = 31 * result + (defaultUsername != null ? defaultUsername.hashCode() : 0);
		return result;
	}

}
