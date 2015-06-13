package project.source.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Searcher 
{
	public Searcher()
	{
		
	}
	
	private String search(String word) throws IOException
	{
		File fileToSearch;
		if((word.charAt(0) <= 'z' && word.charAt(0) >= 'a') || (word.charAt(0) <= 'Z' && word.charAt(0) >= 'A')) fileToSearch = new File(WordOrganizer.DEFAULT_SAVE_DIRECTORY + word.toUpperCase().charAt(0)+".txt");
		else fileToSearch = new File(WordOrganizer.DEFAULT_SAVE_DIRECTORY +"Other.txt");
		String information = "";
		if(fileToSearch.exists())
		{
			FileReader fr = new FileReader(fileToSearch);
			BufferedReader br = new BufferedReader(fr);
			String s = "";
			
			while((s = br.readLine()) != null)
			{
				String[] data = s.split(",");
				if(data[0].equals(word)){
					information = s;
					break;
				}
			}
			
			br.close();
		}
		
		return information;
	}
	
	public ArrayList<Result> searchResult(String word) throws IOException
	{
		String info = search(word);
		ArrayList<Result> result = new ArrayList<>();
		if(info.equals(""));
		else
		{
			String[] data = info.split(",");
			for(int i = 1; i < data.length; i += 2)
			{
				result.add(new Result(data[i], Integer.parseInt(data[i+1])));
			}
			
		}
		return result;
	}
}
