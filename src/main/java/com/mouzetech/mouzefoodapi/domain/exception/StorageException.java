package com.mouzetech.mouzefoodapi.domain.exception;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = -6361066578186312090L;

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message) {
		super(message);
	}
}