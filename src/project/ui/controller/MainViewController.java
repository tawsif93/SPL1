package project.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.source.document.DocumentProcessor;
import project.source.document.DocumentProcessorService;
import project.source.stemmer.Stemmer;
import project.source.search.Result;
import project.source.search.Searcher;

import java.awt.*;
import java.io.File;
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
    private ProgressBar progressBar ;
    @FXML
    private Button buttonView ;
    @FXML
    private Button buttonChangeDirectory ;

    private Scene previousScene ;

    public void setPreviousScene(Scene scene)
    {
        previousScene = scene;
    }

    public void setSource(String source) {
        service.setSource(source);
    }

    @FXML
    public void buttonChangeDirectoryHandler(ActionEvent event)
    {
        Stage backStage =(Stage) ((Node) event.getSource()).getScene().getWindow() ;
        backStage.setScene(previousScene);

        backStage.show();

    }

    @FXML
    public void buttonSearchHandler(ActionEvent event)
    {
        System.out.println(service.getWorkDone());
        System.out.println(service.getTotalWork());

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

    @FXML
    private void handleResultTable() {
        Result result = resultTable.getSelectionModel().getSelectedItem();
        if(resultTable.getSelectionModel().getSelectedItem() != null) {
            String directory = DocumentProcessor.getInputDoc() + File.separator + result.fileName.getValue();

            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(new File(directory));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }).start();
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
