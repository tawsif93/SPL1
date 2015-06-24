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
import project.source.document.DocumentProcessor;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FileChooserViewController extends MainViewController implements Initializable{
    DirectoryChooser directoryChooser = new DirectoryChooser();

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

        Parent mainViewParent;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/MainView.fxml"));

            mainViewParent = fxmlLoader.load();

            MainViewController controller = fxmlLoader.<MainViewController>getController();
            controller.setSource(directoryTextField.getText());
            controller.setPreviousScene(((Node) actionEvent.getSource()).getScene());

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

        if (checkValidDirectory(directoryTextField.getText()))
            buttonGo.setDisable(false);
        else
            buttonGo.setDisable(true);
    }

    public Boolean checkValidDirectory(String path)
    {
        File file = new File(path);
        return file.exists();
    }

    @FXML
    public void onClickDirectory( ActionEvent event)
    {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
        File selectedDirectory = directoryChooser.showDialog(currentStage);


        if(selectedDirectory != null)
        {
            if(checkValidDirectory(selectedDirectory.getAbsolutePath()))
                buttonGo.setDisable(false);

            directoryTextField.setText(selectedDirectory.getAbsolutePath());
        }

    }

	/**
	 *
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  <tt>null</tt> if the location is not known.
	 * @param resources The resources used to localize the root object, or <tt>null</tt> if
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttonGo.setDisable(true);
		configureFileChooser();
		configureDirectoryField();

	}

    public void configureFileChooser()
    {
        directoryChooser.setTitle("Documents Directories");

        directoryChooser.setInitialDirectory(new File("/home/tawsif/Documents/Source/"));
    }

    private void configureDirectoryField() {
        File sourceFile = new File(DocumentProcessor.PARSED_FILE_LIST);
        FileReader fr;
        try {
            fr = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(fr);

            String originalSourceFileName = br.readLine();
            if(originalSourceFileName != null && checkValidDirectory(originalSourceFileName))
            {
                directoryTextField.setText(originalSourceFileName);
                buttonGo.setDisable(false);
            }
            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
