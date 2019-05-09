package com.salesw.exercise.model;

import java.io.Serializable;

public class CreateBoardRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long duration;
	boolean random;
	String board;
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public boolean isRandom() {
		return random;
	}
	public void setRandom(boolean random) {
		this.random = random;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}

}
