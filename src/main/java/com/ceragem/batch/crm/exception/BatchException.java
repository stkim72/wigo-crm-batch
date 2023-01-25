package com.ceragem.batch.crm.exception;

public class BatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4677625852144904674L;

	public BatchException() {
		super();
	}

	public BatchException(String msg) {
		super(msg);
	}

	public BatchException(String message, Exception e) {
		super(message, e);
	}
}
