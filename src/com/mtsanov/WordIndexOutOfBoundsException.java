package com.mtsanov;

import java.io.Serializable;

public class WordIndexOutOfBoundsException extends Exception implements Serializable
{
	public static final long serialVersionUID = 1L;
	private String message;

	public WordIndexOutOfBoundsException(String message)
	{
		this.message = message;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}
}
