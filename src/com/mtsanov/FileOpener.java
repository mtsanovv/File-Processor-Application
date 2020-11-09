package com.mtsanov;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileOpener implements ActionListener
{
	private JFrame frame;
	private JTextField filePath;
	private FileProcessor fileProcessor;
	private FileEditor fileEditor;

	public FileOpener(FileProcessor fileProcessor, JFrame frame, JTextField filePath)
	{
		this.fileProcessor = fileProcessor;
		this.frame = frame;
		this.filePath = filePath;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser c = new JFileChooser();
		c.setCurrentDirectory(new File("."));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.txt)", "txt");
		c.setFileFilter(filter);
		int rVal = c.showOpenDialog(frame);
		if (rVal == JFileChooser.APPROVE_OPTION)
		{
			File fileOpened = c.getSelectedFile();
			try
			{
				fileEditor = new FileEditor(fileOpened);
				fileEditor.setFileContents(fileEditor.readWholeFile());
				int lineCount = fileEditor.getLinesInFile();
				filePath.setText(fileOpened.getAbsolutePath());
				fileProcessor.setFileEditor(fileEditor);
				fileProcessor.addExtraComponents(lineCount, String.join("\n", fileEditor.getFileContents()));
			}
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(frame, "Error when opening file:\n" + ex.getMessage(), "Error opening file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
