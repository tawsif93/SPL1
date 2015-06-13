package project.source;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by tawsif on 6/13/15.
 *
 * @Time 5:00 AM
 */
public class DocumentProcessorService extends Service {
    /**
     * Invoked after the Service is started on the JavaFX Application Thread.
     * Implementations should save off any state into final variables prior to
     * creating the Task, since accessing properties defined on the Service
     * within the background thread code of the Task will result in exceptions.
     * <p>
     * For example:
     * <pre><code>
     *     protected Task createTask() {
     *         final String url = myService.getUrl();
     *         return new Task&lt;String&gt;() {
     *             protected String call() {
     *                 URL u = new URL("http://www.oracle.com");
     *                 BufferedReader in = new BufferedReader(
     *                         new InputStreamReader(u.openStream()));
     *                 String result = in.readLine();
     *                 in.close();
     *                 return result;
     *             }
     *         }
     *     }
     * </code></pre>
     * <p>
     * <p>
     * If the Task is a pre-defined class (as opposed to being an
     * anonymous class), and if it followed the recommended best-practice,
     * then there is no need to save off state prior to constructing
     * the Task since its state is completely provided in its constructor.
     * </p>
     * <p>
     * <pre><code>
     *     protected Task createTask() {
     *         // This is safe because getUrl is called on the FX Application
     *         // Thread and the FirstLineReaderTasks stores it as an
     *         // immutable property
     *         return new FirstLineReaderTask(myService.getUrl());
     *     }
     * </code></pre>
     *
     * @return the Task to execute
     */

    DocumentProcessor processor = DocumentProcessor.createProjectStart();

    @Override
    protected Task createTask() {
        return processor;
    }

    public String getSource()
    {
        return processor.getInputDocument();
    }

    public void setSource (String source)
    {
        processor.setInputDocument(source);
    }
}
