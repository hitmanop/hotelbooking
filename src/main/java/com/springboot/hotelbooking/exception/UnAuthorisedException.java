package com.springboot.hotelbooking.exception;

public class UnAuthorisedException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2185717466785497408L;

	public UnAuthorisedException(String message) {
        super(message);
    }
}
