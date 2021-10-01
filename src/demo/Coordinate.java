package demo;

// Object to serve as a coordinate
public class Coordinate {
	public float x, y;
	
	public Coordinate(int i, int j) {
		this.x = i;
		this.y = j;
	}
	
	public Coordinate(float i, float j) {
		this.x = i;
		this.y = j;
	}
	
	//distance formula (int input)
	public float distanceFrom(int x2, int y2) { //distance formula
		return (float) Math.sqrt(Math.pow((y2 - y), 2) + Math.pow((x2 - this.x), 2));
	}
	
	//distance formula (Coordinate input)
	public float distanceFrom(Coordinate coo) { //distance formula
		return (float) Math.sqrt(Math.pow((coo.y - this.y), 2) + Math.pow((coo.x - this.x), 2));
	}
}
