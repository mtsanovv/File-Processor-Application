package com.mtsan.FileProcessorApplication;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntFilter extends DocumentFilter
{
	private int lowerBound;
	private int upperBound;

	public IntFilter(int lowerBound, int upperBound)
	{
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
	{

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (test(sb.toString()))
		{
			super.insertString(fb, offset, string, attr);
		}
	}

	private boolean test(String text)
	{
		try
		{
			int parsedInt = Integer.parseInt(text);
			if(parsedInt >= lowerBound && parsedInt <= upperBound)
			{
				return true;
			}
			return false;
		}
		catch (NumberFormatException e)
		{
			if(text.length() == 0)
			{
				return true;
			}
			return false;
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
	{

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString()))
		{
			super.replace(fb, offset, length, text, attrs);
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
	{
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (test(sb.toString()))
		{
			super.remove(fb, offset, length);
		}
	}
}
