package com.salesw.exercise.dao;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

@Component
public class SalesWRedissonClient {

	private RedissonClient client;

	public SalesWRedissonClient() {
		super();
		try {
			Config config = new Config();
			config.useSingleServer().setAddress(System.getenv("REDIS_URL"));
			client = Redisson.create(config);
		} catch (Exception e) {
			//Using local map
		}

	}

	public RedissonClient getClient() {
		return client;
	}

	public void setClient(RedissonClient client) {
		this.client = client;
	}

}
