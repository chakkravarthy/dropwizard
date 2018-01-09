package com.demo.db;

import java.util.Collection;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.demo.api.Message;
import com.demo.core.UserFollowerRel;

public interface MessageDAO {

	@SqlUpdate("INSERT INTO messages (message, creationTime, userId) values (:message, :creationTime, :userId)")
	@GetGeneratedKeys
	Integer insertMessage(@Bind("message") String message, @Bind("creationTime") long currentTimeInMillis,
			@Bind("userId") Integer userId);

	@SqlQuery("SELECT id FROM messages WHERE message=:message and userId=:userId")
	Integer getMessageId(@Bind("message") String message, @Bind("userId") Integer userId);

	@SqlBatch("INSERT INTO user_follower_rel (userId, followerId) values (:userId, :followerId)")
	void followUsers(@BindBean Collection<UserFollowerRel> followers);

	@SqlQuery("SELECT t.id,t.message,t.userId,t.creationTime FROM messages t INNER JOIN user_follower_rel ufr ON t.userId=ufr.followerId "
			+ "WHERE ufr.userId=:userId ORDER BY t.creationTime FETCH FIRST :count ROWS ONLY")
	@RegisterMapper(MessageMapper.class)
	List<Message> getTopNMessages(@Bind("count") Integer count, @Bind("userId") Integer userId);

}
