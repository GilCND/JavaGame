package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class serverPlayer1 
{
	piratesonline.Panel panel;
	static int x;
	static int y;
	int width;
	int height;
	static double xvelocity; //horizontal velocity
	double yvelocity; //vertical velocity
	
	//hitBok
	Rectangle hitBox; //Collision checking
	
	//keys
	static boolean kUp;
	static boolean kDown;
	static boolean kLeft;
	static boolean kRight;

	
	//Contructor
		public serverPlayer1(int x, int y, piratesonline.Panel panel)
		{
			//sending to different variable 

			this.panel= panel;
			this.x = x;
			this.y = y;
			 
			//Player size
			width = 50; 
			height = 50;
			
			//hitBox Collision
			hitBox = new Rectangle(x, y, width, height);
					
		}
		
		
		public static double set(boolean left, boolean right, boolean down, boolean up)
		{
			//Movement if's
			if(kLeft && kRight || !kLeft && !kRight)
			{
				xvelocity *= 0.8;
			}
			else if (kLeft && !kRight)
			{
				xvelocity --;
			
			}
			else if (kRight && !kLeft)
			{
				xvelocity ++;
			}
			
			//Prevent sliding
			if (xvelocity > 0 && xvelocity < 0.75)
			{
				xvelocity =0;
			}
			if (xvelocity < 0 && xvelocity > -0.75)
			{
				xvelocity =0;
			}
			
			//Control the max speed
			if (xvelocity > 7)
			{
				xvelocity = 7;
			}
			if (xvelocity < -7)
			{
				xvelocity = -7;
			}
			
			//Jumping
			if (kUp)
			{
			}
			
			return xvelocity;
		}
		
	
		public void draw(Graphics2D g2d)
		{
			g2d.fillRect(x, y, width, height);
			g2d.setColor(Color.darkGray);
			
			Font f = new Font("Arial", Font.BOLD,40);
			g2d.setFont(f);
//			g2d.drawString(Integer.toString(x), 100, 100);
		}

		
	}
