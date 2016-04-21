package ru.dyatel.karaka.boards;

public class Board {

	private String name;
	private String code;
	private boolean readOnly;

	private String postTable;
	private String attachmentTable;

	private int maxAttachmentsSize;
	private int bumpLimit;
	private int maxPages;

	private String defaultUsername;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getAttachmentTable() {
		return attachmentTable;
	}

	public void setAttachmentTable(String attachmentTable) {
		this.attachmentTable = attachmentTable;
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

}
