package project.ui.controller;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import project.source.DocumentProcessor;
import project.source.DocumentProcessorService;
import project.source.search.Result;
import project.source.search.Searcher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{

    DocumentProcessorService service = new DocumentProcessorService();

    @FXML
    private TableView resultTable ;

    @FXML
    private TableColumn  columnKeyWord ;

    @FXML
    private TableColumn columnFrequency ;

    @FXML
    private Button buttonSearch ;

    @FXML
    private TextField enterTextField ; ;

    @FXML
    private ProgressBar progressBar = new ProgressBar();


    public void setSource(String source) {
        service.setSource(source);
    }

    @FXML
    public void onClick(ActionEvent event)
    {
        System.out.println(service.getSource());
        System.out.println(service.getState());
        if(service.getState()== Worker.State.FAILED) service.restart();
        Searcher searcher = new Searcher();
        try {
            System.out.println(enterTextField.getText() + " text ");
          if(enterTextField.getText().length()!= 0)  searcher.searchResult(enterTextField.getText()).forEach(s -> System.out.println(s.fileName + " " + s.frequency));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
