package com.demo.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.demo.api.Message;

public class MessageMapper implements ResultSetMapper<Message> {

	@Override
	public Message map(int index, ResultSet rs, StatementContext ctx) throws SQLException {

		Message tweet = new Message();
		tweet.setCreationTime(rs.getLong("creationTime"));
		tweet.setId(rs.getInt("id"));
		tweet.setMessage(rs.getString("message"));
		tweet.setUserId(rs.getInt("userId"));

		return tweet;
	}

}
