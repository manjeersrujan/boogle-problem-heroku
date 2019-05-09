package com.salesw.exercise.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomStringUtils;

import com.salesw.exercise.exception.SalesWhalesServiceException;

import io.micrometer.core.instrument.util.StringUtils;

public class BoogleBoard implements Serializable {

	private static final long serialVersionUID = 1L;

	private String boardString = null;
	private char[][] board = null;
	private long id = 0L;
	private long expiryTime = 0L;
	private long duration;
	private long points;
	private String token;
	private Set<String> words = new HashSet<>();
	private static AtomicInteger counter = new AtomicInteger(0);

	public BoogleBoard(String boardString, long duration) {
		this.boardString = boardString;
		this.board = generateBoardArray(boardString);
		this.id = generateUniqId();
		this.expiryTime = new Date().getTime() + duration;
		this.points = 0L;
		this.token = generateToken();
		this.duration = duration;
	}

	public Set<String> getWords() {
		return words;
	}

	public void setWords(Set<String> words) {
		this.words = words;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBoardString() {
		return boardString;
	}

	public void setBoardString(String boardString) {
		this.boardString = boardString;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	private char[][] generateBoardArray(String boardString2) {
		if (StringUtils.isBlank(boardString2)) {
			new SalesWhalesServiceException("INVALID_GAME_STRING");
		}

		String[] split = boardString2.split(",");
		if (split.length != 16) {
			new SalesWhalesServiceException("INVALID_GAME_STRING");
		}
		char[][] board = new char[4][4];
		int k = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++, k++) {
				board[i][j] = split[k].trim().charAt(0);
			}
		}
		return board;
	}

	private long generateUniqId() {
		return counter.incrementAndGet();
	}

	private String generateToken() {
		return RandomStringUtils.randomAlphanumeric(30);
	}
}
