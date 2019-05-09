package com.salesw.exercise.exception;

public class SalesWhalesServiceException extends Exception {

	int statusCode;

	public SalesWhalesServiceException(String message) {
		super(message);
	}

	public SalesWhalesServiceException(SalesWhalesServiceError error) {
		super(error.message);
	}

	public SalesWhalesServiceException(Throwable ex) {
		super(ex);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651790075167593475L;

}
