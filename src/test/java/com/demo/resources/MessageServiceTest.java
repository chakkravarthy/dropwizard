package com.demo.resources;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.demo.api.Message;
import com.demo.api.UsersFollowers;
import com.demo.core.UserFollowerRel;
import com.demo.db.MessageDAO;

public class MessageServiceTest {

	private final MessageDAO messageDAO = mock(MessageDAO.class);
	private final MessageService messageService = new MessageService(messageDAO);

	@Test
	public void testCreateMessage() {

		Integer userId = 100;
		String message = "This is my first message";
		Integer messageId = 1;
		long time = System.currentTimeMillis();

		when(messageDAO.insertMessage(message, time, userId)).thenReturn(messageId);

		Message messageObj = new Message();
		messageObj.setUserId(userId);
		messageObj.setMessage(message);
		messageObj.setCreationTime(time);

		Integer genMessageId = messageService.createMessage(messageObj);

		verify(messageDAO).insertMessage(message, time, userId);

		assertThat(genMessageId, is(equalTo(1)));

	}

	@Test
	public void testGetMessageById() {

		Integer userId = 100;
		String messageStr = "This is my first message";
		Integer messageId = 1;
		
		Message message = new Message();
		message.setUserId(userId);
		message.setMessage(messageStr);

		when(messageDAO.getMessageId(messageStr, userId)).thenReturn(messageId);

		Integer genMessageId = messageService.getMessageId(message);

		verify(messageDAO).getMessageId(messageStr, userId);

		assertThat(genMessageId, is(equalTo(1)));

	}

	@Test
	public void testFollowUsers() {

		Collection<UserFollowerRel> followers = new ArrayList<UserFollowerRel>();
		UserFollowerRel ufr = new UserFollowerRel();
		ufr.setFollowerId(101);
		followers.add(ufr);

		doNothing().when(messageDAO).followUsers(followers);
		
		UsersFollowers userFollower = new UsersFollowers();
		List<Integer> followerIds = new ArrayList<>(Arrays.asList(101));;
		userFollower.setFollowerIds(followerIds);

		messageService.followUsers(userFollower);

	}

	@Test
	public void testGetTopNMessages() {

		List<Message> mockMessages = mock(List.class);

		when(messageDAO.getTopNMessages(100, 10)).thenReturn(mockMessages);

		List<Message> messages = messageService.getTopNMessages(100, 10);

		verify(messageDAO).getTopNMessages(100, 10);

		assertThat(messages, is(not(nullValue())));
		
		assertThat(messages.isEmpty(), is(false));

	}

}
