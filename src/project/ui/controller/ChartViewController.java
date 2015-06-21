package project.ui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tawsif on 6/16/15.
 *
 * @Time 12:19 AM
 */
public class ChartViewController implements Initializable{

	@FXML
	private PieChart pieChart;

	@FXML
	private Label pieLabel ;

	@FXML
	private Button buttonBack;

	private Scene previousScene ;

	@FXML
	public void buttonBackHandler(ActionEvent event)
	{
		Stage backStage =(Stage) ((Node) event.getSource()).getScene().getWindow() ;
		backStage.setScene(previousScene);

		backStage.show();
	}

	public void setPieChart(String chartTitle , ObservableList<PieChart.Data> datas) {


		pieChart.setTitle("Word : " + chartTitle);
		pieChart.setData(datas);


		pieChart.getData().forEach(data -> data.getNode().addEventHandler( MouseEvent.MOUSE_ENTERED , event1 -> pieLabel.setVisible(true)) );

		pieChart.getData().forEach(data -> data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			pieLabel.setLayoutX(event.getSceneX());
			pieLabel.setLayoutY(event.getSceneY());
			pieLabel.setText(data.getName() + "\n" + (int) data.getPieValue());
			pieLabel.autosize();
		}));
		pieChart.getData().forEach(data -> data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> pieLabel.setVisible(false)));
	}
	public void setPreviousScene(Scene scene)
	{
		this.previousScene = scene;
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
		pieLabel.setVisible(false);

	}

}
