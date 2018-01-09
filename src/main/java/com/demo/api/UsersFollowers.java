package com.demo.api;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UsersFollowers {

	@NotNull
	private Integer userId;
	@NotEmpty
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
