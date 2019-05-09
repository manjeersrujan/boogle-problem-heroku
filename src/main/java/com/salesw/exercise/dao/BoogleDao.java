package com.salesw.exercise.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.salesw.exercise.model.BoogleBoard;

@Component
public class BoogleDao {

	private Map<Long, BoogleBoard> map = new HashMap<>();

	public void save(BoogleBoard boogleBoard) {
		map.put(boogleBoard.getId(), boogleBoard);
	}

	public BoogleBoard get(long id) {
		return map.get(id);
	}

}
