package com.mr.rc.springjwt.exception;

public class UserNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String msg) {
		super(msg);
	}

}
