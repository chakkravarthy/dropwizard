package com.demo.db;

import java.util.Collection;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import com.demo.core.User;
import com.demo.core.UserFollowerRel;

public interface DBBuilderDAO {

	@SqlUpdate("CREATE TABLE users (id INTEGER, name VARCHAR(50))")
	void createUserTable();

	@SqlBatch("INSERT INTO users (id, name) values (:id, :name)")
	int[] createUsersData(@BindBean Collection<User> users);

	@SqlUpdate("CREATE TABLE messages (id SERIAL primary key, message VARCHAR(160), creationTime LONG, userId INTEGER)")
	void createTweetTable();

	@SqlUpdate("CREATE TABLE user_follower_rel (userId INTEGER, followerId INTEGER)")
	void createFollowersTable();

	@SqlBatch("INSERT INTO user_follower_rel (userId, followerId) values (:userId, :followerId)")
	int[] createFollowersData(@BindBean Collection<UserFollowerRel> followers);
	
}
