package com.springboot.hotelbooking.exception;

public class ResourceNotFoundException extends RuntimeException{
    // Change made by prashant saran singh
    private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
