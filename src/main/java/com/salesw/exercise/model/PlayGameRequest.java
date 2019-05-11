package com.salesw.exercise.model;

import java.io.Serializable;

/**
 * @author yeddulamanjeersrujan
 *
 * May 12, 2019
 *
 */
public class PlayGameRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String token, word;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
