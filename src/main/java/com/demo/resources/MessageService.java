package com.demo.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.demo.api.Message;
import com.demo.api.UsersFollowers;
import com.demo.core.UserFollowerRel;
import com.demo.db.MessageDAO;

@Path("/")
@Timed
public class MessageService {

	private final MessageDAO messageDAO;
	
	public MessageService(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	@POST
	@Path("/message")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Integer createTweet(@Valid Message tweet) {

		return messageDAO.insertTweet(tweet.getMessage(), System.currentTimeMillis(), tweet.getUserId());

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/message/id")
	public String getTweetId(@Valid Message tweet) {

		return messageDAO.getTweet(tweet.getMessage(), tweet.getUserId());

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/message/{id}")
	public boolean updateTweet(@PathParam("id") Integer id, @Valid Message tweet) {

		messageDAO.updateTweet(id, tweet.getMessage(), System.currentTimeMillis());

		return true;

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/follow")
	public boolean followUsers(@Valid UsersFollowers usersFollowers) {

		List<UserFollowerRel> followers = usersFollowers.getFollowerIds().stream()
				.map(followerId -> new UserFollowerRel(usersFollowers.getUserId(), followerId))
				.collect(Collectors.toList());

		messageDAO.followUsers(followers);

		return true;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/feed")
	public List<Message> getTopHundredTweets(@QueryParam("userId") String userId) {

		return messageDAO.getTweets(userId);

	}

	@GET
	@Path("/pingme")
	public String pingMe() {
		return "I am here";
	}

}
