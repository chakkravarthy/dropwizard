package com.demo.health;

import org.eclipse.jetty.util.StringUtil;

import com.codahale.metrics.health.HealthCheck;
import com.demo.resources.MessageService;

public class ServiceHealthCheck extends HealthCheck{

	private MessageService service = null;
	
	public ServiceHealthCheck(MessageService service){
		this.service = service;
	}
	
	@Override
	protected Result check() throws Exception {
		if(StringUtil.isNotBlank(service.pingMe())){
			return Result.healthy();
		}else{
			return Result.unhealthy("MessageService is temporarily down");
		}
	}
	
	
}
