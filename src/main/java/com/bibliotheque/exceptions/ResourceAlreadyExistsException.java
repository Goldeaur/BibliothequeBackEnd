package com.bibliotheque.exceptions;;

public class ResourceAlreadyExistsException extends Exception{
	private static final long serialVersionUID = 1L;
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
}
