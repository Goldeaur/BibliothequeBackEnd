package com.bibliotheque.exception;;

public class ResourceAlreadyExistsException extends Exception{
	private static final long serialVersionUID = 1L;
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
}
