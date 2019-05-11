package com.salesw.exercise.exception;

import java.io.Serializable;

/**
 * @author yeddulamanjeersrujan
 *
 * May 12, 2019
 *
 */
public class SalesWhalesServiceError implements Serializable {

	public SalesWhalesServiceError() {
		super();
	}

	public SalesWhalesServiceError(int status, String errorMessage) {
		super();
		this.status = status;
		this.message = errorMessage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9161253911517761782L;
	int status;
	String message;


	public static SalesWhalesServiceError getGenericError() {
		return new SalesWhalesServiceError(500, "Internal Server Error");
	}

}
