package project.source.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Updater 
{
	private File fileToWrite;

	public Updater(File fileToWrite)
	{
		this.fileToWrite = fileToWrite;
	}
	
	public void update(String word, String source) throws IOException
	{
		String wordToUpdate = word;

		FileReader fr = new FileReader(fileToWrite);
		BufferedReader br = new BufferedReader(fr);
		boolean isNewWord = true;
		String s = "";
		int iteration = 1;
		
		while((s = br.readLine()) != null)
		{
			String[] tokens = s.split(",");
			if(wordToUpdate.equals(tokens[0]))
			{
				isNewWord = false;
				boolean isNewFileName = true;
				for(int i = 1; i < tokens.length; i += 2)
				{
					if(source.equals(tokens[i]))
					{
						updateSourceFileInfo(iteration, i);
						isNewFileName = false;
						break;
					}
				}
				
				if(isNewFileName)
				{
					updateNewSourceFileInfo(source, iteration);
				}
			}
			
			iteration++;
		}
		
		br.close();
		
		if(isNewWord)
		{
			append(wordToUpdate, source);
		}
		
	}
	
	private void updateSourceFileInfo(int iteration, int fileNamePosition) throws IOException
	{
		FileInputStream fstream = new FileInputStream(fileToWrite);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		StringBuilder fileContent = new StringBuilder();
		String s = "";
		int it = 1;
		
		while((s = br.readLine()) != null)
		{
			if(it == iteration)
			{
				String [] data = s.split(",");
				int frequency = Integer.parseInt(data[fileNamePosition + 1]);
				frequency++;
				data[fileNamePosition + 1] = frequency + "";
				
				fileContent.append(data[0]);
				for(int i = 1; i < data.length; i++)
				{
					fileContent.append(",").append(data[i]);
				}
				
				fileContent.append("\n");
			}
			else 
			{
				fileContent.append(s).append('\n');
			}
			
			it++;
		}
		
		br.close();
		FileWriter fStreamWriter = new FileWriter(fileToWrite);
        BufferedWriter out = new BufferedWriter(fStreamWriter);
        out.write(fileContent.toString());
        out.close();
	}
	
	private void append(String wordToUpdate, String source) throws IOException
	{
		FileInputStream fstream = new FileInputStream(fileToWrite);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		StringBuilder fileContent = new StringBuilder();
		String s = "";
		
		while((s = br.readLine()) != null)
		{
			fileContent.append(s).append('\n');
		}
		
		fileContent.append(wordToUpdate).append(",").append(source).append(",").append(1).append("\n");
		
		br.close();
		FileWriter fstreamWrite = new FileWriter(fileToWrite);
        BufferedWriter out = new BufferedWriter(fstreamWrite);
        out.write(fileContent.toString());
        out.close();
	}
	
	private void updateNewSourceFileInfo(String  source, int iteration) throws IOException
	{
		FileInputStream fStream = new FileInputStream(fileToWrite);
		BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
		StringBuilder fileContent = new StringBuilder();
		String s = "";
		int it = 1;
		
		while((s = br.readLine()) != null)
		{
			if(it == iteration)
			{
				s += "," + source + "," + 1;
			}
			
			fileContent.append(s).append('\n');
			it++;
		}
		
		br.close();
		FileWriter fStreamWriter = new FileWriter(fileToWrite);
        BufferedWriter out = new BufferedWriter(fStreamWriter);
        out.write(fileContent.toString());
        out.close();
	}
}
