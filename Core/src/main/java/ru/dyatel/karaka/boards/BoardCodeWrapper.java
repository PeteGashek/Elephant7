package ru.dyatel.karaka.boards;

public class BoardCodeWrapper {

	@BoardCode
	private String boardCode = null;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BoardCodeWrapper that = (BoardCodeWrapper) o;

		return boardCode != null ? boardCode.equals(that.boardCode) : that.boardCode == null;

	}

	@Override
	public int hashCode() {
		return boardCode != null ? boardCode.hashCode() : 0;
	}

	@Override
	public String toString() {
		return boardCode;
	}

}
