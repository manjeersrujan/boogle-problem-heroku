package com.salesw.exercise.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.salesw.exercise.model.BoogleBoard;

/**
 * @author yeddulamanjeersrujan
 *
 * May 12, 2019
 *
 */
@Component
public class BoogleDao {

	private Map<Long, BoogleBoard> map = new HashMap<>();

	/**
	 * @param boogleBoard
	 */
	public void save(BoogleBoard boogleBoard) {

		map.put(boogleBoard.getId(), boogleBoard);
	}

	/**
	 * @param id
	 * @return
	 */
	public BoogleBoard get(long id) {

		return map.get(id);
	}

}
