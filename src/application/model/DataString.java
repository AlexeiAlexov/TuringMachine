package application.model;

import java.util.ArrayList;

import application.view.MainViewController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

public class DataString {
	
	
	// ********************************************
	// *************** CONSTANTS ******************
	// ********************************************
	private static final int ID_INDEX_LABEL = 0;
	private static final int ID_ARROW_LEFT_LABEL = 1;
	private static final int ID_ARROW_RIGHT_LABEL = 2;
	private static final int ID_CLOSE_LABEL = 3;
	private static final int ID_DATA_LABEL = 4;
	private static final int ID_DATA_CENTER_LABEL = 5;
	
	public static final Background BG 
			= new Background(new BackgroundFill(ColorConst.SECOND, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background BG_INDEX_LABEL 
			= new Background(new BackgroundFill(ColorConst.META, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background BG_ARROWS_LABEL 
			= new Background(new BackgroundFill(ColorConst.THIRD, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background BG_DATA_LABEL 
			= new Background(new BackgroundFill(ColorConst.MAIN, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background BG_DATA_CENTER_LABEL 
			= new Background(new BackgroundFill(ColorConst.CENTRAL, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background BG_DATA_ACTIVE_LABEL 
			= new Background(new BackgroundFill(ColorConst.ACTIVE, CornerRadii.EMPTY, Insets.EMPTY));
	
	// spacing between label in main hbox
	public static final int SPACING = 10;
	
	// size of every label in component
	public static final int LABEL_WIDTH = 30;
	public static final int LABEL_HEIGHT = 40;
	
	// amount of space on top and bottom of component
	public static final int H_FRAME = 5;
	// amount of space on sides of component
	public static final int V_FRAME = 5;
	
	// amount of data string labels
	public static final int LABEL_AMOUNT = 11;
	// amount of other labels
	public static final int LABEL_META_AMOUNT = 4;
	// data string component size
	public static final int WIDTH = (LABEL_WIDTH + SPACING) * (LABEL_AMOUNT + LABEL_META_AMOUNT) - SPACING + V_FRAME * 2;  
	public static final int HEIGHT = LABEL_HEIGHT + H_FRAME * 2;

	// ...
	
	// **********************************************
	// ************* INTERFACE PART *****************
	// **********************************************
	private HBox hbox;

	private Label indexLabel;
	private Label moveLeftLabel;
	private Label moveRightLabel;
	private Label removeLabel;
	private Label[] dataLabels;
	
	// ...
	
	// **********************************************
	// ***************** DATA PART ******************
	// **********************************************

	// all flags that setted on current data string
	private ArrayList<Flag> flags;



	
	// this var represent data string
	private String data;
	
	// index of first data string label
	private int index;
	
	// index of center data string label
	private int centerIndex = index + LABEL_AMOUNT / 2;
	
	// ...
	
	public DataString(){
		
		data = "";
		
		moveLeftLabel = new Label();
		setupLabel(moveLeftLabel, ID_ARROW_LEFT_LABEL);
		moveLeftLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				moveIndex(true);
			}
		});
		
		moveRightLabel = new Label();
		setupLabel(moveRightLabel, ID_ARROW_RIGHT_LABEL);
		moveRightLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				moveIndex(false);
			}
		});
		
		removeLabel = new Label();
		setupLabel(removeLabel, ID_CLOSE_LABEL);
		removeLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				remove();
			}
		});
		
		indexLabel = new Label();
		setupLabel(indexLabel, ID_INDEX_LABEL);
		
		dataLabels = new Label[LABEL_AMOUNT];

		
		index = -LABEL_AMOUNT / 2;
		refreshCenterIndex();
		
		hbox = new HBox(SPACING);
		hbox.setPrefSize(WIDTH, HEIGHT);
		hbox.setAlignment(Pos.CENTER);
		hbox.setBackground(BG);
		
		hbox.getChildren().add(indexLabel);
		hbox.getChildren().add(moveLeftLabel);
		
		Label tempL;
		for(int i = 0; i < LABEL_AMOUNT; i++){
			
			tempL = new Label();
			dataLabels[i] = tempL;
			setupLabel(tempL, i == LABEL_AMOUNT / 2 ? ID_DATA_CENTER_LABEL : ID_DATA_LABEL);
			tempL.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.getButton() == MouseButton.PRIMARY){
						
						if (active_index == -1)
							MainViewController.instance.cancelDataStringWaiting();
						
						for(int i = 0; i < dataLabels.length; i++){
							if (dataLabels[i] == (Label)event.getSource()){
								if (active_index == i){
									MainViewController.instance.cancelDataStringWaiting();
									active_index = -1;
									return;
								}
								if (active_index != -1)
									cancel();
								active_index = i;
								break;
							}
						}
						((Label)event.getSource()).setBackground(BG_DATA_ACTIVE_LABEL);
						
						MainViewController.instance.setData(DataString.this);
					}
				}
			});
			hbox.getChildren().add(tempL);
		}
		
		hbox.getChildren().add(moveRightLabel);
		hbox.getChildren().add(removeLabel);
		
		MainViewController.instance.vbox.getChildren().add(MainViewController.instance.vbox.getChildren().size() - 1, getBody());
		
		
	}
	
	
	// cancel waiting for data input
	public void cancel(){
		if (active_index != -1){resetLabelBackground(active_index);active_index = -1;}
	}
	
	private void resetLabelBackground(int index){
		dataLabels[index].setBackground(index == LABEL_AMOUNT / 2 ? BG_DATA_CENTER_LABEL : BG_DATA_LABEL);
	}
	/**
	 * THIS FUNCTION SET LABEL PARAMETERS SUCH AS:
	 * <p> - background</p>
	 * <p> - size</p>
	 * 
	*/
	private static final void setupLabel(Label l, int id){
		
		switch(id){
			case ID_INDEX_LABEL:
				l.setText("I");
				l.setBackground(BG_INDEX_LABEL);
				break;
			case ID_DATA_LABEL:
				l.setBackground(BG_DATA_LABEL);
				break;
			case ID_ARROW_LEFT_LABEL:
				l.setText("<");
				l.setBackground(BG_ARROWS_LABEL);
				break;
			case ID_ARROW_RIGHT_LABEL:
				l.setText(">");
				l.setBackground(BG_ARROWS_LABEL);
				break;
			case ID_DATA_CENTER_LABEL:
				l.setBackground(BG_DATA_CENTER_LABEL);
				break;
			case ID_CLOSE_LABEL:
				l.setBackground(BG_DATA_CENTER_LABEL);
				l.setText("X");
				break;
			default:
				break;
		}
		
		l.setPrefWidth(LABEL_WIDTH);
		l.setPrefHeight(LABEL_HEIGHT);
		l.setAlignment(Pos.CENTER);
		
	}
	
	public void moveIndex(boolean left){
		index += left? -1 : 1;
		refreshCenterIndex();
		refreshData();
	}

	private void refreshCenterIndex(){
		centerIndex = index + LABEL_AMOUNT / 2;
		if (indexLabel != null)indexLabel.setText(centerIndex+"");
	}
	
	public void placeFlag(Flag f, int data_index){
		// todo place index
	}
	
	// this var is used to indicate which label will be flaged
	public int active_index = -1;
	// current refresh data
	private int data_index;
	// amount of free symobls need to add
	private int amount_addition;
	private boolean negative;
	public void setData(char c){
		amount_addition = 0;
		
		resetLabelBackground(active_index);
		data_index = index + active_index;
		
		if (data_index < 0){
			
			amount_addition = -data_index;
			index += amount_addition;
			data_index = 0;
			negative = true;
		}
		else if (data_index > data.length()-1){
			amount_addition = data_index - (data.length() - 1);
			//index -= amount_addition;
			//data_index = data.length()-1;
			negative = false;
		}
		
		if (amount_addition != 0){
			String addition = "";
			for(int i = 0; i < amount_addition; i++){
				addition += " ";
			}
			data = (negative ? addition : "") + data + (!negative ? addition : "");
		}
		
		data = changeData(data_index, c);

		active_index = -1;
		
		
		refreshData();
		
	}
	
	private String changeData(int index, char c){
		return (data.length() != 0 ? data.substring(0, index) : "") + c + (index + 1 < data.length() ? data.substring(index + 1, data.length()) : "");
	}
	
	/**
	 * refresh every data string label, use it after setting new data
	 */
	private void refreshData(){
		refreshCenterIndex();
		for(int i = 0; i < LABEL_AMOUNT; i++)
			refreshLabel(i);
	}
	
	private void refreshLabel(int label_index){
	
		dataLabels[label_index].setText(getData(index + label_index));
	}
	
	public String getData(int index){
		if (index < 0 || index > data.length() - 1)
			return '\0' + "";
		else
			return data.charAt(index) + "";
	}
	
	public void remove(){
		MainViewController.instance.removeDataString(this);
	}
	
	public HBox getBody(){
		return hbox;
	}
	
}
