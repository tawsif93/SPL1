package project.source.document;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

/**
 * Created by tawsif on 6/15/15.
 *
 * @Time 9:18 PM
 */
public class XMLTest {

	public static void main(String[] args) throws IOException, XMLStreamException {
		DocumentProcessor documentProcessor = DocumentProcessor.createProjectStart();

		String directory = "/home/tawsif/Documents/Source/Safe With You lyrics.doc";
//		documentProcessor.getDocumentTimeProperties(directory);

		Path file = Paths.get(directory);
		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

		System.out.println("creationTime: " + attr.creationTime());
		System.out.println("lastAccessTime: " + attr.lastAccessTime());
		System.out.println("lastModifiedTime: " + attr.lastModifiedTime());


		try {
			String line;
			Process p = Runtime.getRuntime().exec("ps -ef");

			BufferedReader input =
					new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if(line.contains("ibus")) System.out.println(line); //<-- Parse data here.
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}

//		System.out.println(new File(directory).lastModified());
//		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss,MM,dd,yyyy ");
//		System.out.println(format.format(new File(directory).lastModified()));
	}

}
