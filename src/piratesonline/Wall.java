package piratesonline;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall 
{
	int x;
	int y;
	int width;
	int height;
	
	//save the position of the wall relative to the other walls
	int startx;
	Color color;
	
	//Collision box 
	Rectangle hitBox;
	
	//Constructor
	
	public Wall (Color color, int x, int y,int width , int height )
	{
		this.x = x;
		startx = x;
		this.y = y;
		this.color = color;
		this.width = width;
		this.height = height;
		
		
		//Hitbox will be where the object are.
		hitBox = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics2D g2d)
	{
		//draw border
		g2d.setColor(Color.YELLOW);
		g2d.drawRect(x, y, width, height);
		//fill the rectangle
		g2d.setColor(color);
		g2d.fillRect(x+1, y+1, width-2, height-2);//the minus here is used to not draw over the line
		
	}
	
	public int set(int camerax)
	{
		x = startx + camerax;
		//update hitBox
		hitBox.x = x;
		
		return x;
	}
	
	
}
