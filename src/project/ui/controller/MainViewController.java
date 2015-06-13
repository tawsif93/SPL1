package project.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import project.source.document.DocumentProcessorService;
import project.source.stemmer.Stemmer;
import project.source.search.Result;
import project.source.search.Searcher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{

    DocumentProcessorService service = new DocumentProcessorService();
    Searcher searcher ;
    Stemmer stem ;
    @FXML
    private TableView<Result> resultTable ;
    @FXML
    private TableColumn<Result , String>  columnKeyWord ;
    @FXML
    private TableColumn<Result , Integer> columnFrequency ;
    @FXML
    private Button buttonSearch ;
    @FXML
    private TextField enterTextField ;
    @FXML
    private ProgressBar progressBar = new ProgressBar();

    public void setSource(String source) {
        service.setSource(source);
    }

    @FXML
    public void onClick(ActionEvent event)
    {
        try {

          if(enterTextField.getText().length()!= 0)  {

              stem.setWord(enterTextField.getText());
              String stemmed = stem.getWord();

              searcher.searchResult(stemmed).forEach(s -> System.out.println(s.fileName + " " + s.frequency));
              refreshList(searcher.searchResult(stemmed));
          }

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

        searcher = new Searcher();
        stem = new Stemmer();
        columnKeyWord.setCellValueFactory(cellData -> cellData.getValue().fileName);
        columnFrequency.setCellValueFactory(cellData -> cellData.getValue().frequency.asObject());


//        columnKeyWord.setCellValueFactory(new PropertyValueFactory<Result , String>("fileName"));
//        columnFrequency.setCellValueFactory(new PropertyValueFactory<Result , Integer>("frequency"));

        simulateProgressBar();

    }


    public void simulateProgressBar()
    {
        progressBar.progressProperty().bind(service.progressProperty());
        if (service.getState() == Worker.State.READY) {
            service.start();
        }
    }

    public void refreshList(ArrayList list)
    {
        ObservableList data = FXCollections.observableArrayList(list);

        System.out.println(data.size());

        resultTable.setItems(data);
        resultTable.requestFocus();

    }
}
