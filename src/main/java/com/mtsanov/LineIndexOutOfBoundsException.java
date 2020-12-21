package com.mtsanov;

import java.io.Serializable;

public class LineIndexOutOfBoundsException extends Exception implements Serializable
{
	public static final long serialVersionUID = 2L;
	private String message;

	public LineIndexOutOfBoundsException(String message)
	{
		this.message = message;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}
}