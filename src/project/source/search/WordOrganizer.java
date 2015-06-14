package project.source.search;

import java.io.File;
import java.io.IOException;

public class WordOrganizer 
{
	public static String DEFAULT_SAVE_DIRECTORY = "Organized" + File.separator ;
	private String sourceFileName;
	
	public WordOrganizer(String sourceFileName)
	{
		setFileName(sourceFileName);
	}
	
	public String getFileName()
	{
		return sourceFileName;
	}
	
	public void setFileName(String sourceFileName)
	{
		this.sourceFileName = sourceFileName;
	}
	
	public void organize(String word) throws IOException
	{
		String fileNameToStore = getFileNameWhereToStore(word);
		File toStore = new File(fileNameToStore);
		
		if(!toStore.exists())
		{
			toStore.createNewFile();
		}
		
		Updater u = new Updater(toStore);
		
		u.update(word, sourceFileName);
	}
	
	private String getFileNameWhereToStore(String word)
	{
		if(word.length() == 0) return DEFAULT_SAVE_DIRECTORY + "Other.txt";

		if((word.charAt(0) <= 'z' && word.charAt(0) >= 'a') || (word.charAt(0) <= 'Z' && word.charAt(0) >= 'A'))
			return DEFAULT_SAVE_DIRECTORY + word.toUpperCase().charAt(0) + ".txt";
		return DEFAULT_SAVE_DIRECTORY + "Other.txt";
	}
}
