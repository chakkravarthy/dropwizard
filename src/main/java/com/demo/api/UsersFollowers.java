package com.demo.api;

import java.util.List;

public class UsersFollowers {

	private Integer userId;
	private List<Integer> followerIds;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getFollowerIds() {
		return followerIds;
	}

	public void setFollowerIds(List<Integer> followerIds) {
		this.followerIds = followerIds;
	}

}
