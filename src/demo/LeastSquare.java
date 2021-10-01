package demo;

public class LeastSquare {
	
	
	//Method that squares a number
	public static double sq(float n) {
		return n * n;
	}
	
	//Method that finds the area of a rectangle given a diagonal d and a leg l
	private static float area(float d, float l) {
		return (float) (l * Math.sqrt(Math.abs(sq(d) - sq(l))));
	}
	
	//finds the area of the least square given two points and the circle's radius
	public static float findArea(Coordinate origin, MyButton point, float r) {
		float var1 = Math.abs(point.center.distanceFrom(origin)); //dist between point and origin
		float diagonal = Math.abs(var1 - r); //dist between point and point of intersection in the circle
		float var2 = diagonal / var1; //ratio between dist from intersection point and distance from origin
		float var3 = Math.abs(point.center.y - origin.y); //vertical distance between point and origin
		float leg = var2 * var3; //a leg in the rectangle (refer to doc)
		float area = area(diagonal, leg);
		return area; //call aux method
	}
}
