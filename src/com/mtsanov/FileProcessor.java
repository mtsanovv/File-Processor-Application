package com.mtsanov;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FileProcessor
{
	private JFrame frame;

	private JTextArea fileDataBox;
	private NumberedJTextArea numberedFileDataBox;

	private JTextField filePath;
	private FileEditor fileEditor;

	private Container pane;

	private JLabel swapLinesLabel;
	private JFormattedTextField swapLine1;
	private JLabel swapLinesWithLabel;
	private JFormattedTextField swapLine2;
	private JButton swapLinesButton;

	private JLabel swapWordFromLabel1;
	private JFormattedTextField swapWordLine1;
	private JLabel swapWordWithIndexLabel1;
	private JFormattedTextField swapWordIndex1;
	private JLabel swapWordFromLabel2;
	private JFormattedTextField swapWordLine2;
	private JLabel swapWordWithIndexLabel2;
	private JFormattedTextField swapWordIndex2;
	private JButton swapWordsButton;

	private JButton saveFileButton;

	private boolean addedExtraComponents = false;

	private void addComponentsToPane(Container pane)
	{
		this.pane = pane;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		fileDataBox = new JTextArea(10, 10);
		Insets margin = new Insets(5,10,5,10);
		numberedFileDataBox = new NumberedJTextArea(fileDataBox, margin);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.ipadx = 0;
		c.gridwidth = 9;
		c.gridx = 0;
		c.gridy = 0;
		fileDataBox.setEditable(false);
		fileDataBox.setMargin(margin);

		JScrollPane fileDataScroller = new JScrollPane(fileDataBox);
		fileDataScroller.setRowHeaderView(numberedFileDataBox);
		pane.add(fileDataScroller, c);

		JButton loadFile = new JButton("Load File");
		c.ipadx = 120;
		c.ipady = 15;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		pane.add(loadFile, c);

		filePath = new JTextField();
		filePath.setBackground(new Color(255, 255, 255));
		c.ipadx = 360;
		c.ipady = 20;
		c.weightx = 0;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 7;

		JScrollPane pathScroller = new JScrollPane(filePath);
		pane.add(pathScroller, c);

		loadFile.addActionListener(new FileOpener(this, frame, filePath));
	}

	public void addExtraComponents(int lineCount, String fileContents)
	{
		fileDataBox.setText(fileContents);
		fileDataBox.setCaretPosition(0);
		GridBagConstraints c = new GridBagConstraints();
		if(lineCount > 0)
		{
			numberedFileDataBox.updateLineNumbers();
		}
		else
		{
			numberedFileDataBox.removeLineNumbers();
		}

		if(!addedExtraComponents)
		{
			addedExtraComponents = true;
			swapLinesLabel = new JLabel("Swap line: ");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 20;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 2;
			pane.add(swapLinesLabel, c);

			swapLine1 = new JFormattedTextField();
			swapLine1.setColumns(8);
			PlainDocument doc = (PlainDocument) swapLine1.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
			c.fill = GridBagConstraints.NONE;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 2;
			pane.add(swapLine1, c);

			swapLinesWithLabel = new JLabel("with line: ");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 20;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 2;
			pane.add(swapLinesWithLabel, c);

			swapLine2 = new JFormattedTextField();
			swapLine2.setColumns(8);
			doc = (PlainDocument) swapLine2.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
			c.fill = GridBagConstraints.NONE;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 3;
			c.gridy = 2;
			pane.add(swapLine2, c);

			swapLinesButton = new JButton("Swap lines");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 4;
			c.gridy = 2;
			pane.add(swapLinesButton, c);

			swapLinesButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						fileDataBox.setText(fileEditor.swapLines(Integer.parseInt(swapLine1.getText()), Integer.parseInt(swapLine2.getText())));
						fileDataBox.setCaretPosition(0);
					}
					catch(NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(frame, "You need to enter all line numbers before swapping lines.", "Error when swapping lines", JOptionPane.ERROR_MESSAGE);
					}
					catch(LineIndexOutOfBoundsException ex)
					{
						JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error when swapping lines", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			swapWordFromLabel1 = new JLabel("Swap from line: ");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 20;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 3;
			pane.add(swapWordFromLabel1, c);

			swapWordLine1 = new JFormattedTextField();
			swapWordLine1.setColumns(8);
			doc = (PlainDocument) swapWordLine1.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
			c.fill = GridBagConstraints.NONE;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = 3;
			pane.add(swapWordLine1, c);

			swapWordWithIndexLabel1 = new JLabel("word with index: ");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 20;
			c.ipadx = 20;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 3;
			pane.add(swapWordWithIndexLabel1, c);

			swapWordIndex1 = new JFormattedTextField();
			swapWordIndex1.setColumns(8);
			doc = (PlainDocument) swapWordIndex1.getDocument();
			doc.setDocumentFilter(new IntFilter(0, Integer.MAX_VALUE));
			c.fill = GridBagConstraints.NONE;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 3;
			c.gridy = 3;
			pane.add(swapWordIndex1, c);

			swapWordFromLabel2 = new JLabel("   with a word from line: ");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 20;
			c.ipadx = 20;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 4;
			c.gridy = 3;
			pane.add(swapWordFromLabel2, c);

			swapWordLine2 = new JFormattedTextField();
			swapWordLine2.setColumns(8);
			doc = (PlainDocument) swapWordLine2.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
			c.fill = GridBagConstraints.NONE;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 5;
			c.gridy = 3;
			pane.add(swapWordLine2, c);

			swapWordWithIndexLabel2 = new JLabel("  and the word has an index: ");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 20;
			c.ipadx = 20;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 6;
			c.gridy = 3;
			pane.add(swapWordWithIndexLabel2, c);

			swapWordIndex2 = new JFormattedTextField();
			swapWordIndex2.setColumns(8);
			doc = (PlainDocument) swapWordIndex2.getDocument();
			doc.setDocumentFilter(new IntFilter(0, Integer.MAX_VALUE));
			c.fill = GridBagConstraints.NONE;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 7;
			c.gridy = 3;
			pane.add(swapWordIndex2, c);

			swapWordsButton = new JButton("Swap words");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 8;
			c.gridy = 3;
			pane.add(swapWordsButton, c);

			swapWordsButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						fileDataBox.setText(fileEditor.swapWords(Integer.parseInt(swapWordLine1.getText()), Integer.parseInt(swapWordLine2.getText()), Integer.parseInt(swapWordIndex1.getText()), Integer.parseInt(swapWordIndex2.getText())));
						fileDataBox.setCaretPosition(0);
					}
					catch(NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(frame, "You need to enter all word indexes required (indexes start from 0) with their respective line numbers before swapping words.", "Error when swapping words", JOptionPane.ERROR_MESSAGE);
					}
					catch(LineIndexOutOfBoundsException ex)
					{
						JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error when swapping words", JOptionPane.ERROR_MESSAGE);
					}
					catch(WordIndexOutOfBoundsException ex)
					{
						JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error when swapping words", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			saveFileButton = new JButton("Save file");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 0;
			c.ipadx = 0;
			c.weightx = 0;
			c.gridwidth = 1;
			c.gridx = 4;
			c.gridy = 4;
			pane.add(saveFileButton, c);

			saveFileButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						fileEditor.saveFile();
						JOptionPane.showMessageDialog(frame, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					}
					catch(IOException ex)
					{
						JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error when saving to file", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}

		if(lineCount > 1)
		{
			//swap lines stuff is only meaningful if there's more than 1 line
			swapLinesLabel.setVisible(true);
			swapLine1.setVisible(true);
			swapLinesWithLabel.setVisible(true);
			swapLine2.setVisible(true);
			swapLinesButton.setVisible(true);
			//change swap lines filters
			PlainDocument doc = (PlainDocument) swapLine1.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
			doc = (PlainDocument) swapLine2.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
		}
		else
		{
			swapLinesLabel.setVisible(false);
			swapLine1.setVisible(false);
			swapLinesWithLabel.setVisible(false);
			swapLine2.setVisible(false);
			swapLinesButton.setVisible(false);
		}

		if(lineCount > 0)
		{
			//swap words stuff is only meaningful if there's at least 1 line
			swapWordFromLabel1.setVisible(true);
			swapWordLine1.setVisible(true);
			swapWordWithIndexLabel1.setVisible(true);
			swapWordIndex1.setVisible(true);
			swapWordFromLabel2.setVisible(true);
			swapWordLine2.setVisible(true);
			swapWordWithIndexLabel2.setVisible(true);
			swapWordIndex2.setVisible(true);
			swapWordsButton.setVisible(true);
			//change swap words filters (only for the lines)
			PlainDocument doc = (PlainDocument) swapWordLine1.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
			doc = (PlainDocument) swapWordLine2.getDocument();
			doc.setDocumentFilter(new IntFilter(1, lineCount));
			saveFileButton.setVisible(true);

		}
		else
		{
			swapWordFromLabel1.setVisible(false);
			swapWordLine1.setVisible(false);
			swapWordWithIndexLabel1.setVisible(false);
			swapWordIndex1.setVisible(false);
			swapWordFromLabel2.setVisible(false);
			swapWordLine2.setVisible(false);
			swapWordWithIndexLabel2.setVisible(false);
			swapWordIndex2.setVisible(false);
			swapWordsButton.setVisible(false);
			saveFileButton.setVisible(false);
		}
		frame.pack();
		frame.setVisible(true);
	}
	private void createAndShowGUI()
	{
		frame = new JFrame("File Processing Application");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	public void setFileEditor(FileEditor fileEditor)
	{
		this.fileEditor = fileEditor;
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FileProcessor().createAndShowGUI();
			}
		});
	}
}
