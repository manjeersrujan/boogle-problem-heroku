package com.salesw.exercise.model;

import java.io.Serializable;

public class PlayGameResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String token;
	private long duration;
	private String board;
	private long time_left;
	private long points;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public long getTime_left() {
		return time_left;
	}
	public void setTime_left(long time_left) {
		this.time_left = time_left;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
}
