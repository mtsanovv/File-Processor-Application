package com.mtsanov;

import javax.swing.*;
import javax.swing.text.Element;
import java.awt.*;

public class NumberedJTextArea extends JTextArea
{
	private JTextArea textArea;

	public NumberedJTextArea(JTextArea textArea, Insets margin)
	{
		this.textArea = textArea;
		setMargin(margin);
		setBackground(Color.LIGHT_GRAY);
		setEditable(false);
	}

	public void updateLineNumbers()
	{
		String lineNumbersText = getLineNumbersText();
		setText(lineNumbersText);
	}

	public void removeLineNumbers()
	{
		setText("");
	}

	public String getLineNumbersText()
	{
		int caretPosition = textArea.getDocument().getLength();
		Element root = textArea.getDocument().getDefaultRootElement();
		StringBuilder lineNumbersTextBuilder = new StringBuilder();
		lineNumbersTextBuilder.append("1").append(System.lineSeparator());

		for (int elementIndex = 2; elementIndex < root.getElementIndex(caretPosition) + 2; elementIndex++)
		{
			lineNumbersTextBuilder.append(elementIndex).append(System.lineSeparator());
		}

		return lineNumbersTextBuilder.toString();
	}
}
