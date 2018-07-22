package com.elephant.seven.util;

public class StringUtil {

	public static int utf8ByteLength(String s) {
		int count = 0;
		int length = s.length();
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);

			if (c <= 0x7F) count++;
			else if (c <= 0x7FF) count += 2;
			else if (Character.isHighSurrogate(c)) {
				count += 4;
				++i;
			} else count += 3;
		}
		return count;
	}

}
