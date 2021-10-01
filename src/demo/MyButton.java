package demo;

import javax.swing.JButton;

// Auxiliary expansion of JButton
public class MyButton extends JButton{
	
	public Coordinate center;
	public Coordinate tl; //top left
	public Coordinate bl; //bottom left
	public Coordinate tr; //top right
	public Coordinate br; //bottom right
	
	
	//Calculate position according to pre-set dimensions:
	// - squares are 6x6px
	// - there is a 10px spacing between squares
	public int calcHorPos(int y) {
		return (10 + y * 16) + 3;
	}
	
	public int calcVerPos(int x) {
		return (10 + x * 16) + 3;
	}
	
	public void setCorners() {
		tl = new Coordinate(center.x - 3, center.y - 3);
		bl = new Coordinate(center.x - 3, center.y + 3);
		tr = new Coordinate(center.x + 3, center.y - 3);
		br = new Coordinate(center.x + 3, center.y + 3);
	}
}
