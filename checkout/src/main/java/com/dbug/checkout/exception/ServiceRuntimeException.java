package com.dbug.checkout.exception;

public class ServiceRuntimeException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 5238294989318638152L;

	/**
	 * Default constructor 
	 */
	public ServiceRuntimeException() {
	}

	/**
	 * @param msg
	 */
	public ServiceRuntimeException(String msg) {
		super(msg);
	}

	/**
	 * @param exception
	 */
	public ServiceRuntimeException(Throwable exception) {
		super(exception);
	}

	/**
	 * @param msg
	 * @param exception
	 */
	public ServiceRuntimeException(String msg, Throwable exception) {
		super(msg, exception);
	}

}
