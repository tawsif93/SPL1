package project.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import project.source.DocumentProcessor;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

    @FXML
    private TableView resultTable ;

    @FXML
    private Button buttonSearch ;

    @FXML
    private TextField enterTextField ;

    @FXML
    private ProgressBar progressBar = new ProgressBar(); ;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DocumentProcessor processor = DocumentProcessor.createProjectStart();


                for(int i = 1 ; i <= processor.pathSender().length ; i++ )
                {
                    System.out.println("Pass");
                    progressBar.setProgress((double)i/ processor.pathSender().length);
                }
//        progressBar.setProgress(.5);
    }
}
