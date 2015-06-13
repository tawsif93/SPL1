package project.ui.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/FileChooserView.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add( getClass().getResource("/application.css").toExternalForm());

        primaryStage.setTitle("Multilingual Text Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
