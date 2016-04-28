package application.view;

import java.util.ArrayList;

import application.model.DataString;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainViewController {

	// FXML ELEMENTS
	
	@FXML
	public VBox vbox;

	@FXML
	public AnchorPane anchorPane;
	
	// GENERATED ELEMENTS
	
	private Label addDataStringLabel;
	
	
	
	// VARIABLES
	public static MainViewController instance;

	// this var show if we waiting for data string input
	public boolean isWaitForData;
	private DataString activeDataString;
	
	public ArrayList<DataString> dataStrings;
	
	public void initialize(){
		
		dataStrings = new ArrayList<DataString>();
		
		anchorPane.setBackground(DataString.BG_DATA_LABEL);
		
		instance = this;
		
		addDataStringLabel = new Label("+");
		addDataStringLabel.setAlignment(Pos.CENTER);
		addDataStringLabel.setMinSize(DataString.WIDTH, DataString.HEIGHT);
		addDataStringLabel.setBackground(DataString.BG);
		addDataStringLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {addDataString();};
		});
		vbox.setPrefWidth(DataString.WIDTH);
		vbox.setPrefWidth(DataString.HEIGHT);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.getChildren().add(addDataStringLabel);
		
		
	}
	
	@FXML
	private void addDataString(){
		vbox.setPrefSize(vbox.getPrefWidth(), vbox.getPrefHeight() + DataString.HEIGHT);
		dataStrings.add(new DataString());
	}
	
	public void cancelDataStringWaiting(){
		if (isWaitForData){
			activeDataString.cancel();
			activeDataString = null;
			isWaitForData = false;	
		}
		
		
	}
	
	public void removeDataString(DataString ds){
		vbox.setPrefSize(vbox.getPrefWidth(), vbox.getPrefHeight() - (DataString.HEIGHT));
		vbox.getChildren().remove(ds.getBody());
	}
	public void setData(DataString ds){
		activeDataString = ds;
		isWaitForData = true;
	}
	
	public void handleKey(KeyCode kc){
		if (kc.isDigitKey() || kc.isLetterKey()){
			activeDataString.setData(kc.getName().charAt(0));
			
			isWaitForData = false;
			activeDataString = null;
			
		}
	}
	
}
