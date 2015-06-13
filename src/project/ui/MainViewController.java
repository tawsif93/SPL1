package project.ui;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import project.source.DocumentProcessor;
import project.source.DocumentProcessorService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{

    DocumentProcessorService service = new DocumentProcessorService();
    @FXML
    private TableView resultTable ;
    @FXML
    private Button buttonSearch ;
    @FXML
    private TextField enterTextField ; ;
@FXML
    private ProgressBar progressBar = new ProgressBar();

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */


    public void setSource(String source) {
        service.setSource(source);
    }

    @FXML
    public void onClick(ActionEvent event)
    {
        System.out.println(service.getSource());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        simulateProgressBar();
    }

    public void simulateProgressBar()
    {
        progressBar.progressProperty().bind(service.progressProperty());
        if (service.getState() == Worker.State.READY) {
            service.start();
        }
    }
}
