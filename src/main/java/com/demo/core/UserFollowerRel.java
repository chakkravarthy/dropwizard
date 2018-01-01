package com.demo.core;

public class UserFollowerRel {

	private Integer userId;
	private Integer followerId;

	public UserFollowerRel() {
	}

	public UserFollowerRel(Integer userId, Integer followerId) {
		this.userId = userId;
		this.followerId = followerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Integer followerId) {
		this.followerId = followerId;
	}

}
