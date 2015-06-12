package project.source;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by tawsif on 6/9/15.
 * @author tawsif
 *
 * This class Reads Text Documents form doc, docx , pdf formatted file
 * Apache Poi Library used for doc
 * itext library used for pdf
 *
 * This Class based on Parsing Text from DOCX file
 * For docx format , basic xml of docx is parsed by unzipping the .docx file
 * xml file is collected from word documemt directory of unzipped .docx file
 * root word directory of docx is "word/document.xml"
 * By formatting the basic xml file , parsed the text document
 *
 */
public class DocumentProcessor {

    private static final String INPUT_DOCUMENT = "/home/tawsif/Documents/Source";
    protected static final String OUTPUT_FOLDER = "Parsed Text/";
    private static final String ZIP_OUTPUT_FOLDER = "Unzipped File/";
    protected static final String PARSED_FILE_LIST = "Parsed List/list.txt";
    private static final String SOURCE_FILE_LIST = "Parsed List/source list.txt";

    private List<String> SourceName = new ArrayList<>();

    public int count ;

    private DocumentProcessor() {
        count = 0 ;
    }

    public static DocumentProcessor createProjectStart() {
        return new DocumentProcessor();
    }

    /**
     * It takes directory of the zipped file ( DOCX )
     * Name of the input file is passed , then added with the directory of Input Folder
     * It creates a output folder with xml files in the output Directory in the program
     * Java ZipEntry and ZipInputStream is used unzipping zip file using byte Array
     *
     * @param filePath name of the input zip file
     *
     */
    public void unzip(String filePath) {

        String string = filePath;

        string = string.replaceAll(".docx", "");

        final String INPUT_ZIP_SOURCE = INPUT_DOCUMENT + File.separator
                + filePath;

        final String OUTPUT_FILE = ZIP_OUTPUT_FOLDER + File.separator + string;

        byte[] buffer = new byte[1024];

        try {
            File mainFoleder = new File(OUTPUT_FILE);

            if (!mainFoleder.exists()) {
                mainFoleder.mkdir();
            }


            ZipInputStream zis = new ZipInputStream(new FileInputStream(
                    INPUT_ZIP_SOURCE));
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();

                File newFile = new File(OUTPUT_FILE + File.separator + fileName);

                System.out.println("File Unzip : " + newFile.getAbsolutePath());

                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0)
                    fos.write(buffer, 0, len);

                fos.close();
                ze = zis.getNextEntry();

            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * @return list of source file
     */
    public String[] pathSender() {

        String[] path;
        File source = new File(INPUT_DOCUMENT);
        path = source.list();
        return path;
    }


    public String xmlFormat(String xml) {
        try {
            InputSource src = new InputSource(new StringReader(xml));
            Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            Boolean keepDeclaration = xml.startsWith("<?xml");

            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> parseText(String s) {

        ArrayList<String> tagText = new ArrayList<>();

        String[] tokens = s.split("\n");

        for (String tok : tokens) {
            if (tok.contains("<w:t>") || tok.contains("</w:t>") || tok.contains("<w:t xml:space=\"preserve\">")) {
                tagText.add(tok.replace("<w:t>", "").replace("</w:t>", "").replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&").replace("&apos;", "'").replace("&quot;", "\"").replace("<w:t xml:space=\"preserve\">", "").trim());
            }
        }
        tagText.removeAll(Arrays.asList("", ".", ",", ":", ";", "\"", " "));
        return tagText;
    }

    public void xmlParse(String formatedXML, String fileName) throws FileNotFoundException {

        ArrayList<String> textList = parseText(formatedXML);

        PrintStream out = new PrintStream(new FileOutputStream(fileName));
        System.setOut(out);

        System.out.println(fileName.replace(".txt" , ".docx").replace(OUTPUT_FOLDER , "") + "\n");

        textList.forEach(System.out::println);

        out.close();

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

    }


    public void parseDocx(String filename) throws IOException {
        unzip(filename);

        final String unformattedPath = ZIP_OUTPUT_FOLDER + filename.replaceAll(".docx", "") + File.separator + "word/document.xml";

        String unformattedXML = readFile(unformattedPath);
        String formattedXML = xmlFormat(unformattedXML);
        xmlParse(formattedXML, OUTPUT_FOLDER + filename.replaceAll(".docx", ".txt"));
    }

    public void doWork() throws IOException {
        String[] path = pathSender();

        loadParsed();

        for (String string : path) {
            if (checkParsed(string)) {

                String withoutExtension = removeExtension(string);

                editList(string +"\n" , SOURCE_FILE_LIST);
                editList(withoutExtension + "\n" , PARSED_FILE_LIST);

                SourceName.add(withoutExtension);

                if (string.contains(".docx")) {
                    parseDocx(string);
                    deleteFiles(withoutExtension);

                } else if (string.contains(".doc")) {

                    String doc = readDoc(INPUT_DOCUMENT + File.separator + string);
                    makeFile(string + "\n "+ doc, string);

                } else if (string.contains(".pdf")) {
                    String pdf = readPdf(INPUT_DOCUMENT + File.separator + string);
                    makeFile(string + "\n "+ pdf, string);
                }
            } else
                System.out.println(string + " : Already Parsed ");

            count++;
        }

    }

    public void makeFile(String value, String fileName) {
        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(OUTPUT_FOLDER + removeExtension(fileName) + ".txt");
            outputStream.write(value.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readFile(String XMLpath) throws IOException {
        Path path = Paths.get(XMLpath);
        StringBuilder sb = new StringBuilder();
        Files.lines(path, StandardCharsets.UTF_8).forEach((str) -> sb.append(str));

        return sb.toString();
    }

    public boolean checkParsed(String fileName) {
        return !SourceName.contains(removeExtension(fileName));
    }

    public String removeExtension(String fileName) {
        if (fileName.contains(".docx")) {
            return fileName.replaceAll(".docx", "");
        } else if (fileName.contains(".doc")) {
            return fileName.replaceAll(".doc", "");
        } else if (fileName.contains(".pdf")) {
            return fileName.replaceAll(".pdf", "");
        }
        return fileName;
    }

    public void editList(String name , String path) {
        try {
            byte[] bytes = name.getBytes();
            FileOutputStream editingFile = new FileOutputStream(path, true);
            editingFile.write(bytes);
            editingFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadParsed() {
        Path parsed = Paths.get(PARSED_FILE_LIST);
        try {
            Files.lines(parsed, StandardCharsets.UTF_8).forEach(s -> SourceName.add(s.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readDoc(String filePath) {

        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(filePath));
            HWPFDocument doc = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(doc);
            return extractor.getText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readPdf(String pdf) {

        PdfReader reader;
        StringBuilder out = null;
        try {
            reader = new PdfReader(pdf);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            out = new StringBuilder();
            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                out.append(strategy.getResultantText());
            }
            reader.close();

            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert out != null;
        return out.toString();
    }

    public void deleteFiles(String path) {
        File index = new File(ZIP_OUTPUT_FOLDER + path);

        delete(index);
    }

    private void delete(File file) {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : " + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {

                    File fileDelete = new File(file, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : " + file.getAbsolutePath());
                }
            }
        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }
}

