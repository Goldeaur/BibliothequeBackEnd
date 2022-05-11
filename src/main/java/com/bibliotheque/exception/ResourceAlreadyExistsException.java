package com.bibliotheque.exception;;

public class ResourceAlreadyExistsException extends Exception{
	private static final Long serialVersionUID = 1L;
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
}
