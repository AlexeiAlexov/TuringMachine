package application.model;

import application.view.MainViewController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class Cell {

	public static final int WIDTH = 30;
	public static final int HEIGHT = 50;
	
	public static final Background BG
		= new Background(new BackgroundFill(ColorConst.CENTRAL, CornerRadii.EMPTY, Insets.EMPTY)); 
	
	public DataString dataString;
	
	public int dataStringPosition;
	public int position;
	
	private Label body;
	
	public Cell(DataString ds, int position){
		this.dataString = ds;
		this.position = position;
		
		dataStringPosition = 0;
		for(DataString tds : MainViewController.instance.dataStrings){
			if (tds == ds)break;
			dataStringPosition++;
		}
		
		body = new Label();
		body.setMinSize(WIDTH, HEIGHT);
		body.setBackground(BG);
		refreshData();
	}
	
	public void move(boolean left){
		if (left)position--;else position++;
	}
	
	public void refreshData(){
		body.setText("");
	}
	
	public Label getBody(){
		return body;
	}
}
