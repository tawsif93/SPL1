package project.ui.controller;/**
 * Created by tawsif on 6/13/15.
 *
 * @Time 2:22 AM
 */

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileChooserViewController implements Initializable{
    DirectoryChooser directoryChooser = new DirectoryChooser();
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */


    @FXML
    private Button buttonDirectory ;
    @FXML
    private Button buttonGo;
    @FXML
    private Label labelDirectory ;
    @FXML
    private TextField directoryTextField ;

    @FXML
    public void onClickGo(ActionEvent actionEvent)
    {
        System.out.println("GO Pass");

        Parent mainViewParent = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/MainView.fxml"));

            mainViewParent = (Parent)fxmlLoader.load();

            MainViewController controller = fxmlLoader.<MainViewController>getController();
            controller.setSource(directoryTextField.getText());

            Scene mainViewScene = new Scene(mainViewParent) ;
            Stage mainViewStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow() ;

            mainViewStage.setScene(mainViewScene) ;
            mainViewStage.show() ;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onKeyTypeTextField(Event event) {

        System.out.println("type pass");
        if (checkValidDirectory(directoryTextField.getText()))
            buttonGo.setDisable(false);
        else
            buttonGo.setDisable(true);
    }

    @FXML
    public void onClickDirectory( ActionEvent event)
    {
        System.out.println("pass");
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        File selectedDirectory = directoryChooser.showDialog(currentStage);


        if(selectedDirectory != null)
        {
            if(checkValidDirectory(selectedDirectory.getAbsolutePath()))
                buttonGo.setDisable(false);

            directoryTextField.setText(selectedDirectory.getAbsolutePath());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonGo.setDisable(true);
        configureFileChooser();
    }

    public void configureFileChooser()
    {
        directoryChooser.setTitle("Documents Directories");

        directoryChooser.setInitialDirectory(new File("/home/tawsif/Documents/Source/"));
    }

    public Boolean checkValidDirectory(String path)
    {
        File file = new File(path);
        return file.exists();
    }


}
