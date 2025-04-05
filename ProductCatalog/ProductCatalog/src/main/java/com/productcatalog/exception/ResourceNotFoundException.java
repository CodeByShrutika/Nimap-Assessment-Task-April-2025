package com.productcatalog.exception;

public class ResourceNotFoundException extends CustomException {

	private static final long serialVersionUID = 1L;


	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
