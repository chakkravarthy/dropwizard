package com.demo.Application;

import org.skife.jdbi.v2.DBI;

import com.demo.ApplicationConfiguration.MessageAppConfiguration;
import com.demo.db.DBBuilderDAO;
import com.demo.db.MessageDAO;
import com.demo.health.ServiceHealthCheck;
import com.demo.resources.DBModelBuilderService;
import com.demo.resources.MessageService;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MessageApplication extends Application<MessageAppConfiguration> {

	public static void main(final String[] args) throws Exception {
		new MessageApplication().run(args);
	}

	@Override
	public String getName() {
		return "TweetService";
	}

	@Override
	public void initialize(final Bootstrap<MessageAppConfiguration> bootstrap) {
		// TODO: application initialization
	}

	@Override
	public void run(final MessageAppConfiguration configuration, final Environment environment) {
		final DBIFactory factory = new DBIFactory();
		
		final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");
		
		final MessageDAO tweetDAO = jdbi.onDemand(MessageDAO.class);
		final DBBuilderDAO dbBuilderDAO = jdbi.onDemand(DBBuilderDAO.class);
		
		MessageService tweetService = new MessageService(tweetDAO);
		
		environment.jersey().register(tweetService);
		environment.jersey().register(new DBModelBuilderService(dbBuilderDAO));
		
		environment.healthChecks().register("service", new ServiceHealthCheck(tweetService));

	}

}
