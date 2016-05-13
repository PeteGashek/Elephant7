package ru.dyatel.karaka.boards;

public class BoardCodeWrapper {

	@BoardCode
	private String boardName = null;

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BoardCodeWrapper that = (BoardCodeWrapper) o;

		return boardName != null ? boardName.equals(that.boardName) : that.boardName == null;

	}

	@Override
	public int hashCode() {
		return boardName != null ? boardName.hashCode() : 0;
	}

}
