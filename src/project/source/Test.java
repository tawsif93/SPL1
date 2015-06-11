package project.source;

/**
 * Created by tawsif on 6/10/15.
 *
 * @Time 1:05 PM
 */
public class Test {

    public static void main(String[] args) {

        DocumentProcessor processor = DocumentProcessor.createProjectStart();
        for (String s : processor.pathSender())
            System.out.println(s);

        try {
            processor.doWork();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
