package com.mtsan.FileProcessorApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class FileEditor
{
	private File file;
	private ArrayList<String> fileContents;
	private int linesInFile = 0;

	public FileEditor(File file)
	{
		this.file = file;
	}

	public ArrayList<String> readWholeFile() throws IOException
	{
		linesInFile = 0;
		ArrayList fileContents = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				linesInFile++;
				fileContents.add(line);
			}
		}

		return fileContents;
	}

	public String swapLines(int line1, int line2) throws LineIndexOutOfBoundsException
	{
		if(line1 != line2 && line1 > 0 && line2 <= linesInFile)
		{
			Collections.swap(fileContents, line1 - 1, line2 -1);
		}
		else
		{
			throw new LineIndexOutOfBoundsException("The line numbers need to be different and most importantly, to exist.\nAvailable line numbers: from 1 to " + linesInFile);
		}
		return String.join("\n", fileContents);
	}

	public String swapWords(int line1, int line2, int wordIndex1, int wordIndex2) throws LineIndexOutOfBoundsException, WordIndexOutOfBoundsException
	{
		if(line1 != line2 && line1 > 0 && line2 <= linesInFile)
		{
			String line1Data = fileContents.get(line1 - 1);
			String line2Data = fileContents.get(line2 - 1);

			//cover the two delimiters - tabs and spaces
			//also cover multiple delimiters of the same type, next to each other
			String delimiter1 = "[\t]+";
			String joinDelimiter1 = "\t";
			if (line1Data.indexOf(" ") != -1)
			{
				//whitespaces are the priority delimiter
				delimiter1 = "[ ]+";
				//when saving the final result to the file -
				//well, the multiple delimiters of the same type, next to each other will be converted to only one
				joinDelimiter1 = " ";
			}
			String delimiter2 = "[\t]+";
			String joinDelimiter2 = "\t";
			if (line2Data.indexOf(" ") != -1)
			{
				//whitespaces are the priority delimiter
				delimiter2 = "[ ]+";
				//when saving the final result to the file -
				//well, the multiple delimiters of the same type, next to each other will be converted to only one
				joinDelimiter2 = " ";
			}

			String[] line1Array = line1Data.split(delimiter1, Integer.MAX_VALUE - 1);
			String[] line2Array = line2Data.split(delimiter2, Integer.MAX_VALUE - 1);
			if(wordIndex1 < 0 || wordIndex1 > line1Array.length - 1)
			{
				throw new WordIndexOutOfBoundsException("The index supplied for the word on line " + line1 + " is invalid.\nAvailable word indexes on line " + line1 + ": from 0 to " + (line1Array.length - 1));
			}
			if(wordIndex2 < 0 || wordIndex2 > line2Array.length - 1)
			{
				throw new WordIndexOutOfBoundsException("The index supplied for the word on line " + line2 + " is invalid.\nAvailable word indexes on line " + line2 + ": from 0 to " + (line2Array.length - 1));
			}
			String word1Data = line1Array[wordIndex1];
			line1Array[wordIndex1] = line2Array[wordIndex2];
			line2Array[wordIndex2] = word1Data;

			fileContents.set(line1 - 1, String.join(joinDelimiter1, line1Array));
			fileContents.set(line2 - 1, String.join(joinDelimiter2, line2Array));
		}
		else
		{
			throw new LineIndexOutOfBoundsException("The line numbers need to be different and most importantly, to exist.\nAvailable line numbers: from 1 to " + linesInFile);
		}
		return String.join("\n", fileContents);
	}

	public void saveFile() throws IOException
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
		{
			for(int i = 0; i < fileContents.size(); i++)
			{
				//remove trailing new line
				if(i == fileContents.size() - 1)
				{
					bw.write(fileContents.get(i));
				}
				else
				{
					bw.write(fileContents.get(i) + "\n");
				}
			}
		}
	}

	public int getLinesInFile()
	{
		return linesInFile;
	}

	public ArrayList<String> getFileContents()
	{
		return fileContents;
	}

	public void setFileContents(ArrayList<String> fileContents)
	{
		this.fileContents = fileContents;
	}
}
