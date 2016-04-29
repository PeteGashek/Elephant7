package ru.dyatel.karaka.threads;

public class Post {

	private Long postId = 0L;
	private Long threadId = 0L;

	private Long timestamp = 0L;

	private PostType type = PostType.NORMAL;

	private String name = "";
	private String message = null;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getThreadId() {
		return threadId;
	}

	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public PostType getType() {
		return type;
	}

	public void setType(PostType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Post post = (Post) o;

		if (!postId.equals(post.postId)) return false;
		if (!threadId.equals(post.threadId)) return false;
		if (!timestamp.equals(post.timestamp)) return false;
		if (type != post.type) return false;
		if (!name.equals(post.name)) return false;
		return message != null ? message.equals(post.message) : post.message == null;

	}

	@Override
	public int hashCode() {
		int result = postId.hashCode();
		result = 31 * result + threadId.hashCode();
		result = 31 * result + timestamp.hashCode();
		result = 31 * result + type.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + (message != null ? message.hashCode() : 0);
		return result;
	}

}
