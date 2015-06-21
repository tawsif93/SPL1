package project.source.document;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

/**
 * Created by tawsif on 6/15/15.
 *
 * @Time 9:18 PM
 */
public class XMLTest {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		DocumentProcessor documentProcessor = DocumentProcessor.createProjectStart();

		String directory = "/home/tawsif/Desktop/Document/Bill 15./docProps/core.xml";
//		documentProcessor.getTimeStamp(directory);
		System.out.println(new File(directory).lastModified());
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss,MM,dd,yyyy ");
		System.out.println(format.format(new File(directory).lastModified()));
	}

}
