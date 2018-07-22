package com.elephant.seven.util;

import com.elephant.seven.posts.Post;

import java.util.List;

public class PostUtil {

	public static void sortByThreadIdList(List<Post> posts, List<Long> threadIds) {
		if (threadIds.size() != posts.size())
			throw new IllegalArgumentException("different amount of posts and thread ids!");
		int current = 0;
		for (Long id : threadIds) {
			for (int i = current; i < posts.size(); i++) {
				if (id.equals(posts.get(i).getThreadId())) {
					Post temp = posts.get(i);
					posts.set(i, posts.get(current));
					posts.set(current, temp);
					current++;
					break;
				}
			}
		}
	}

}
