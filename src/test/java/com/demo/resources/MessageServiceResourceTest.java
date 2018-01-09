package com.demo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.demo.api.Message;
import com.demo.api.UsersFollowers;
import com.demo.core.UserFollowerRel;
import com.demo.db.MessageDAO;

import io.dropwizard.testing.junit.ResourceTestRule;

public class MessageServiceResourceTest {

	private static final MessageDAO DAO = mock(MessageDAO.class);

	@ClassRule
	public static final ResourceTestRule RESOURCES = ResourceTestRule.builder().addResource(new MessageService(DAO))
			.build();

	private Message message = new Message();
	private Integer messageId;

	@Before
	public void setup() {

		message.setMessage("First Message");
		message.setCreationTime(new Long(1515482509));
		message.setUserId(100);
		messageId = 1;
	}

	@After
	public void tearDown() {
		reset(DAO);
	}

	@Test
	public void testCreateMessage() {

		when(DAO.insertMessage(message.getMessage(), message.getCreationTime(), message.getUserId()))
				.thenReturn(messageId);

		final Response response = RESOURCES.target("/message").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE));

		verify(DAO).insertMessage("First Message", new Long(1515482509), 100);
		
		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());		
	}

	@Test
	public void testGetMessageId() {

		when(DAO.getMessageId(message.getMessage(), message.getUserId()))
				.thenReturn(messageId);

		final Response response = RESOURCES.target("/message/id").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE));

		verify(DAO).getMessageId(message.getMessage(), message.getUserId());
		
		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
		
	}
	
	@Test
	public void testGetTopNMessages() {
		
		List<Message> mockMessages = new ArrayList<Message>();
		mockMessages.add(new Message());

		when(DAO.getTopNMessages(100, message.getUserId()))
				.thenReturn(mockMessages);
		
		final List<Message> messages = RESOURCES.target("/feed/100").queryParam("userId",100).request()
				.get(new GenericType<List<Message>>(){});

		verify(DAO).getTopNMessages(100, message.getUserId());
		
		assertThat(messages).isNotEmpty();
		
	}

	@Test
	public void testFollowUsers() {
		
		UsersFollowers usersFollowers = new UsersFollowers();
		
		Collection<UserFollowerRel> followers = new ArrayList<UserFollowerRel>();
		UserFollowerRel ufr = new UserFollowerRel();
		ufr.setFollowerId(101);
		followers.add(ufr);

		doNothing().when(DAO).followUsers(followers);

		RESOURCES.target("/follow").request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.entity(usersFollowers, MediaType.APPLICATION_JSON_TYPE));

	}
	
}
