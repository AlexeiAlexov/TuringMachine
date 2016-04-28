package application.model;

import java.util.ArrayList;

public class Flag {

	private static int ID_GEN = 0;
	
	public ArrayList<Cell> cells;
	
	public int dataStringAmount;
	
	public final int id;
	
	
	public Flag(){
		
		cells = new ArrayList<>();
		
		id = ID_GEN++;
		
	}
	
	public void move(boolean left){
		for(Cell c : cells)
			c.move(left);
	}
	
	
}
