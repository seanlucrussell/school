// P5 Assignment
// Author: Sean Russell
// Date:   Apr 13, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.util.ArrayList;

public class ChessPiece {
	protected int row;
	protected int column;
	protected String color;
	
	public int getRow(){
		return row;
	}
	public void setRow(int row){
		this.row = row;
	}
	public int getColumn(){
		return column;
	}
	public void setColumn(int column){
		this.column = column;
	}
	public String getColor(){
		return color;
	}
	public String getPosition(){
		return Character.toString((char) (column + 97)) + (row + 1);
	}
	
	public ChessPiece(String color, int row, int column){
		this.color = color;
		setRow(row);
		setColumn(column);
	}
	
	public ArrayList<String> legalMoves(ChessPiece [][] board){
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public static void main(String[] args){
		ChessPiece c = new ChessPiece("white",4,3);
		System.out.println(c.getPosition());
	}
	
	
}
