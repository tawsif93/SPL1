package project.source.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileOrganizer 
{	
	public FileOrganizer()
	{
		
	}
	
	public void organize(String sourceFileName) throws IOException
	{
		File sourceFile = new File(sourceFileName);	
		FileReader fr = new FileReader(sourceFile);
		BufferedReader br = new BufferedReader(fr);
		
		String originalSourceFileName = br.readLine();
		String word = "";
		WordOrganizer wo = new WordOrganizer(originalSourceFileName);
		while((word = br.readLine()) != null)
		{
			wo.organize(word);
		}
		
		br.close();
	}
}
