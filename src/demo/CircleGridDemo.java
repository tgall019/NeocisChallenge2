package demo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class CircleGridDemo extends JFrame{
	
	public static CircleGridDemo frame; //frame made static for convenience
	
	public static MyButton[][] grid = new MyButton[20][20]; //square grid
	public static ArrayList<MyButton> blueButtons = new ArrayList<>(); //aux list of blue buttons
	public static JButton execute; //execute button
	
	public static Coordinate btnCoo = new Coordinate(0, 0); //auxiliary Coordinate obj for a button
	public static Coordinate coo; //auxiliary Coordinate obj
	public static double currentRadius2; //auxiliary variable to hold radius of circle
	public static float farthestRadius2 = 0; //aux variable to hold radius of outer red circle
	public static float closestRadius2 = 0; //aux variable to hold radius of inner red circle
	
	//The rate at which the least square algorithm will look for a best fit. 
	//The larger the value, the less precise, but the less expensive.
	public static float RoC = 1; //A value of 1 works well.
	
	//Constructor that sets up the frame
	public CircleGridDemo() {
		//Structures the grid in the frame
		int xPos = 10, yPos = 10; //hard-coded design structure element
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				// Set up every button
				grid[i][j] = new MyButton();
				grid[i][j].center = new Coordinate(xPos + 3, yPos + 3);
				grid[i][j].setCorners();
				grid[i][j].setBounds(xPos, yPos, 6, 6);
				grid[i][j].setBackground(Color.gray);
				grid[i][j].setBorder(null);
				grid[i][j].addMouseListener(new Part2Listener(i, j));
				add(grid[i][j]);
				
				xPos += 16;
			}
			
			xPos = 10;
			yPos += 16;
		}
		
		// Set up the execute button
		execute = new JButton("Generate");
		execute.setBounds(98, 335, 150, 37);
		execute.setBackground(Color.lightGray);
		execute.addMouseListener(new Part2Listener(0, 0));
		add(execute);
	}

	// ### MAIN CLASS ###
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui(); 
            }
        });
    }
	
	public static void gui() {
		//Frame initiation
		frame = new CircleGridDemo();
		frame.getContentPane().setBackground(Color.white);
		frame.setSize(346, 420);
		frame.setLocationRelativeTo(null);
		frame.add(new CirclePanel2());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//Finds the estimated-best origin point for the circle
	private Coordinate findOrigin() {
		float xTotal = 0, yTotal = 0;
		//find sum of coordinates of all buttons along x and y
		for(MyButton i : blueButtons) {
			xTotal += i.center.x;
			yTotal += i.center.y;
		}
		
		//find averages
		xTotal = xTotal / blueButtons.size();
		yTotal = yTotal / blueButtons.size();
		
		return new Coordinate(xTotal, yTotal);
	}
	
	//Finds the best-fit radius for the circle
	private void findRadius() {
		closestRadius2 = 500; //arbitrary high number
		farthestRadius2 = 0; //arbitrary low number
		float leastSquareTotal = 10000; //arbitrary high number
		float currentSquareTotal = 0; //aux variable to hold current square total
		
		//find closest and farthest points (from the origin) and store their distances
		for(MyButton i : blueButtons) {
			if(i.center.distanceFrom(btnCoo) > farthestRadius2) {farthestRadius2 = i.center.distanceFrom(btnCoo);}
			if(i.center.distanceFrom(btnCoo) < closestRadius2) {closestRadius2 = i.center.distanceFrom(btnCoo);}
		}
		
		//Try every radius from closestRadius to farthestRadius with an increment of RoC (defined above)
		for(int r = (int) Math.floor(closestRadius2); r <= Math.ceil(farthestRadius2); r += RoC) {
			
			//Find the least square total for all blue points, and find the sum of it
			for(MyButton i : blueButtons) {
				currentSquareTotal += LeastSquare.findArea(btnCoo, i, r); 
			}
			
			//if the current radius hits a new low, the value will be stored
			if(currentSquareTotal < leastSquareTotal) {
				leastSquareTotal = currentSquareTotal;
				currentRadius2 = r;
			}
			currentSquareTotal = 0; //reset aux variable
		}
	}
	
	//Mouse Listener for Part 2
	class Part2Listener implements MouseListener{
		int x, y;
		
		public Part2Listener(int i, int j) {
			this.x = i; this.y = j;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {		
			//If event is triggered by the Execute button...
			if(e.getSource() == execute) {
				btnCoo = findOrigin(); //find the approximated best origin for the circle
				findRadius(); //find the best radius
				frame.update(getGraphics()); //update the graphics to show circle
			}
			//Else...
			else {
				//unmark square button
				if(grid[x][y].getBackground() == Color.blue) {
					grid[x][y].setBackground(Color.gray);
					blueButtons.remove(grid[x][y]);
				}
				//mark square button
				else {
					grid[x][y].setBackground(Color.blue);
					blueButtons.add(grid[x][y]);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			//Change color to black when cursor enters, unless blue
			if(grid[x][y].getBackground() != Color.blue && grid[x][y].getBackground() != Color.yellow) {
				grid[x][y].setBackground(Color.black);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//Change color back to gray when cursor exits, unless blue
			if(grid[x][y].getBackground() != Color.blue && grid[x][y].getBackground() != Color.yellow) {
				grid[x][y].setBackground(Color.gray);
			}
		}
	}
}

//Panel that draws a circle for Part 2
class CirclePanel2 extends JPanel{	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		this.setOpaque(false);
		
		//Blue Circle
		g.setColor(Color.blue);
		g.drawArc((int) CircleGridDemo.btnCoo.x - (int)Math.round(CircleGridDemo.currentRadius2),
				(int)CircleGridDemo.btnCoo.y - (int)Math.round(CircleGridDemo.currentRadius2), 
				(int)Math.round(CircleGridDemo.currentRadius2)*2,
				(int)Math.round(CircleGridDemo.currentRadius2)*2,
				0, 360);
	}
}



	





