package project.source.search;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Result
{
	public SimpleStringProperty fileName;
	public SimpleIntegerProperty frequency;
	
	public Result(String fileName, int frequency)
	{
		this.fileName = new SimpleStringProperty(fileName);
		this.frequency = new SimpleIntegerProperty(frequency);
	}
	
}
