package com.demo.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
	public Integer createMessage(@Valid Message message) {

		return messageDAO.insertMessage(message.getMessage(), message.getCreationTime(), message.getUserId());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/message/id")
	public Integer getMessageId(@Valid Message message) {

		return messageDAO.getMessageId(message.getMessage(), message.getUserId());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/feed/{count}")
	public List<Message> getTopNMessages(@PathParam("count") Integer count,
			@Valid @NotNull @QueryParam("userId") Integer userId) {

		return messageDAO.getTopNMessages(count, userId);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/follow")
	public void followUsers(@Valid UsersFollowers usersFollowers) {

		List<UserFollowerRel> followers = usersFollowers.getFollowerIds().stream()
				.map(followerId -> new UserFollowerRel(usersFollowers.getUserId(), followerId))
				.collect(Collectors.toList());

		messageDAO.followUsers(followers);
	}

	@GET
	@Path("/pingme")
	public String pingMe() {
		return "Service is running";
	}

}
