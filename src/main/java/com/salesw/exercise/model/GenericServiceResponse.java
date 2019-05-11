package com.salesw.exercise.model;

import java.io.Serializable;


/**
 * @author yeddulamanjeersrujan
 *
 * May 12, 2019
 *
 * @param <T>
 */
public class GenericServiceResponse<T> implements Serializable {
	
	public GenericServiceResponse() {
		super();
	}
	public GenericServiceResponse(String status, String statusMessage, T payload) {
		super();
		this.status = status;
		this.message = statusMessage;
		this.payload = payload;
	}

	private static final long serialVersionUID = 8923237327134793040L;
	String status;
	String message;
	T payload;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}

}
