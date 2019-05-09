package com.salesw.exercise.model;

import java.io.Serializable;

public class CreateBoardRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long duration;
	Boolean random;
	String board;
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Boolean isRandom() {
		return random;
	}
	public void setRandom(Boolean random) {
		this.random = random;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}

}
