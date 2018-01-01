package com.demo.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.demo.core.User;
import com.demo.core.UserFollowerRel;
import com.demo.db.DBBuilderDAO;

@Path("/dbsetup")
public class DBModelBuilderService {

	private DBBuilderDAO dbBuilderDAO;

	public DBModelBuilderService(DBBuilderDAO dbBuilderDAO) {
		this.dbBuilderDAO = dbBuilderDAO;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String createTables() {

		dbBuilderDAO.createUserTable();

		dbBuilderDAO.createTweetTable();

		dbBuilderDAO.createFollowersTable();

		List<User> users = new ArrayList<User>();

		int i = 0;

		User ref = null;

		while (i < 200) {

			i++;

			ref = new User();

			ref.setId(i);
			ref.setName("user" + i);

			users.add(ref);

		}

		dbBuilderDAO.createUsersData(users);

		int userIndex = 0;

		List<UserFollowerRel> followers = new ArrayList<UserFollowerRel>();

		for (int j = 0; j < users.size(); j++) {

			UserFollowerRel follower = new UserFollowerRel();

			if (j % 5 == 0) {
				userIndex = j;
				continue;
			}

			follower.setUserId(users.get(userIndex).getId());
			follower.setFollowerId(users.get(j).getId());

			followers.add(follower);

		}

		dbBuilderDAO.createFollowersData(followers);

		return "SUCCESS";

	}

}
