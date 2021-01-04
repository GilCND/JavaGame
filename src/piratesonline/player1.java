package piratesonline;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class player1 
{
	Panel panel;
	int x;
	int y;
	int width;
	int height;
	double xvelocity; //horizontal velocity
	double yvelocity; //vertical velocity
	
	//hitBok
	Rectangle hitBox; //Collision checking
	
	//keys
	 boolean kUp;
	 boolean kDown;
	 boolean kLeft;
	 boolean kRight;

	
	//Contructor
		public player1(int x, int y, Panel panel)
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
			
			//player image 
//			private MTardis TheTardis;
//			
//			public TheDoctor() {
//				super("dw12.png", 100, 200);
//			}
//			
//			public void setTardis (MTardis tempTardis) {
//				this.TheTardis = tempTardis;
//			}
					
		}
		

		
		public void set()
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
				//Check if touches the ground
				hitBox.y ++; //one pixel below the player
				for(Wall wall: panel.walls) 
				{
					if(wall.hitBox.intersects(hitBox))
					{
						yvelocity=-9; //allows just 1 jump getting the floor as reference
						
					}
				}
				hitBox.y --;
			}
			
			yvelocity += 0.4;
			
			//Horizontal Collision
			hitBox.x += xvelocity;
			for(Wall wall: panel.walls)
			{
				if(hitBox.intersects(wall.hitBox))
				{
					hitBox.x -= xvelocity;
					while (!wall.hitBox.intersects(hitBox)) hitBox.x += Math.signum(xvelocity);
					hitBox.x -= Math.signum(xvelocity);
					panel.camerax += x - hitBox.x;
					xvelocity = 0;
					hitBox.x = x;
					
				}
			}
			
			//Vertical Collision
			hitBox.y += yvelocity;
			for(Wall wall: panel.walls)
			{
				if(hitBox.intersects(wall.hitBox))
				{
					hitBox.y -= yvelocity;
					while (!wall.hitBox.intersects(hitBox)) hitBox.y += Math.signum(yvelocity);
					hitBox.y -= Math.signum(yvelocity);
					panel.camerax += y - hitBox.y;
					yvelocity = 0;
					hitBox.y = y;
				}
			}
			
			
			//move the player
			panel.camerax -= xvelocity; //it runs in the background, in order to no have inverse control keys i'm using - sign
			y += yvelocity;
			
			//Move the hitBox of the player along with the player
			hitBox.x = x;
			hitBox.y = y;
			
			//When player dies
			if (y>800)
			{
				panel.restart();
			}
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
