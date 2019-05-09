package com.salesw.exercise.dao;

import java.util.HashMap;
import java.util.Map;

import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesw.exercise.model.BoogleBoard;

@Component
public class BoogleDao {

	private Map<Long, BoogleBoard> map = new HashMap<>();

	private final String BOOGLE_MAP = "BOOGLE_MAP";

	@Autowired
	SalesWRedissonClient salesWRedissonClient;

	public void save(BoogleBoard boogleBoard) {

		if (salesWRedissonClient != null && salesWRedissonClient.getClient() != null) {
			RMap<Long, BoogleBoard> map = salesWRedissonClient.getClient().getMap(BOOGLE_MAP);
			map.put(boogleBoard.getId(), boogleBoard);
			return;
		}

		map.put(boogleBoard.getId(), boogleBoard);
	}

	public BoogleBoard get(long id) {

		if (salesWRedissonClient != null && salesWRedissonClient.getClient() != null) {
			RMap<Long, BoogleBoard> map = salesWRedissonClient.getClient().getMap(BOOGLE_MAP);
			return map.get(id);
		}

		return map.get(id);
	}

}
