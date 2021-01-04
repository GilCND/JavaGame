package piratesonline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;


public class Panel extends javax.swing.JPanel implements ActionListener
{

	player1 player1;
	//image to player 
	Random rand = new Random();
	
	//points 
	int score;
	static int savedScore;
	int life =3;
	int counter;
	int tmpPlayID;
	String command;
	String response, response2;
	
	//Walls
	//Array list to store all walls
	public ArrayList<Wall> walls = new ArrayList<>();
	Timer gTimer;
	int offset;
	public int camerax;
	public String userName;
	
	//buttons
	Rectangle Reset;
	Rectangle Smenu;
	Rectangle Save;
	Rectangle Load;
	
	//font 
	Font font = new Font("Arial", Font.ITALIC, 30);

	static int SOCKET_PORT;
	static Socket s;
	PrintWriter out;
	
	OutputStream outstream;
	InputStream instream;
	Scanner in;
	
	private Menu menu;
	
	public static enum STATE
	{
		MENU,
		GAME
	};
	private STATE State = STATE.MENU;
	
	//Constructor for a Panel
	public Panel () throws IOException
	{ 	
		
		SOCKET_PORT = 5556;
		s = new Socket("127.0.0.1", SOCKET_PORT);
		
		//initialize an output stream
		outstream = s.getOutputStream();
		out = new PrintWriter(outstream);
		
		//initialize an input stream to receive data back from the server
		instream = s.getInputStream();
		in = new Scanner(instream);	

		
		do {
            userName = JOptionPane.showInputDialog("Please enter your name: ");
            command = "NEWPLAYER "+userName+" \n";
    		out.print(command);
    		out.flush();
    		response = in.nextLine();
    		tmpPlayID = Integer.valueOf(response);
    		
		}while(userName == null);
		
		
		//positioning Buttons on screen
		Reset = new Rectangle(550,25,50,50);
		Smenu = new Rectangle(600,25,50,50);
		
		//Player1 position
		player1 = new player1 (400, 300, this);
		restart(); 
		menu = new Menu();
		
		
		gTimer = new Timer();
		gTimer.schedule(new TimerTask(){

			@Override
			public void run() 
			{	
				//Check if we need to respown new walls
				
				if(walls.get(walls.size()-1).x < 800)
				{
					offset += 700; //in order to not print wall one top of another. 700 create a mew screem
					makeWalls(offset);
				}
				
				//update the player's position 
				player1.set();
				for(Wall wall: walls) //for int i=0; i<walls; i++
				{
					wall.set(camerax);
				}
				
				//Delete old wall in order to not use unecessary resources
				//Delete Walls
				for (int i =0; i<walls.size(); i++)
				{
					if (walls.get(i).x < -100)
					{
						score = score +1;
						walls.remove(i);
					}
				}
				
				repaint();
			
			}
			
		},0, 17);//how long it takes to start the timer (0 seconds), How many miliseconds between frames 1000/60 = 16.6
	}
	

	//when player dies

	public void reset()
	{
		//restart player position
	
			player1.x = 200;
			player1.y = 0;
			player1.xvelocity = 0;
			player1.yvelocity = 0;
			//clear walls and rebuild scenario
			walls.clear();
			offset = -150;
			makeWalls(offset);
			camerax = 150;
			counter = 0;
			life = 3;
			score = 0;
			savedScore =0;
			counter ++;
		}

	
	public void restart()
	{
		//restart player position

		player1.x = 200;
		player1.y = 0;
		player1.xvelocity = 0;
		player1.yvelocity = 0;
		//clear walls and rebuild scenario
		walls.clear();
		offset = -150;
		makeWalls(offset);
		camerax = 150;	
		if (counter ==0)
		{			
			command = "SAVESCORE "+tmpPlayID+" "+savedScore+" \n";
			out.print(command);
			out.flush();
			score = 0;
			savedScore =0;
		}
		else
		{
			if (life >0)
			{
				save();	
				load();
				life --;
				command = "SAVEDEATH "+tmpPlayID+" \n";
				out.print(command);
				out.flush();				
			}
			else
			{
				command = "SAVEDEATH "+tmpPlayID+" \n";
				out.print(command);
				out.flush();
				score = 0;	
				savedScore =0;
				life = 3;
				State = STATE.MENU;
			}		

		}
		counter ++;
			
	}
	
	public void save ()
	{
		savedScore = score;
		command = "SAVESCORE "+tmpPlayID+" "+savedScore+" \n";
		out.print(command);
		out.flush();
		response = in.nextLine();	
		
	}
	public void load ()
	{
		score = savedScore;	
		
	}
	
	public void makeWalls(int offset)
	{
		
		
		int size = 50;
		int map = rand.nextInt(6); //ammount of maps
		//floor
		
		if(map == 0)
		{
			
			for(int i =0; i<14; i++)
			{
				walls.add(new Wall(Color.ORANGE, offset + i*50, 600, size, size));
			}

		}
		else if (map == 1)
		{
			
			for (int i=0; i<5; i++)
			{
				walls.add(new Wall(Color.red, offset + i*50, 600, size, size));
			}
			walls.add(new Wall(Color.red,offset + 500, 600, size, size));
			walls.add(new Wall(Color.red,offset + 550, 600, size, size));
			walls.add(new Wall(Color.red,offset + 600, 600, size, size));
			walls.add(new Wall(Color.red,offset + 650, 600, size, size));
			walls.add(new Wall(Color.red,offset + 700, 600, size, size));
			walls.add(new Wall(Color.red,offset + 750, 600, size, size));		

		}

		else if (map == 2)
		{
			
			for (int i=0; i<5; i++)
			{
				walls.add(new Wall(Color.ORANGE, offset + i*50, 600, size, size));
			}
			for (int i=0; i<5; i++)
			{
				walls.add(new Wall(Color.ORANGE, offset + 450 + i*50, 600, size, size));
			}
			walls.add(new Wall(Color.ORANGE, offset + 150 + 550, 600, size, size));
			walls.add(new Wall(Color.ORANGE, offset + 200 + 550, 600, size, size));
			walls.add(new Wall(Color.ORANGE, offset + 200 + 500, 600, size, size));
			walls.add(new Wall(Color.ORANGE, offset + 200 + 450, 600, size, size));
			walls.add(new Wall(Color.ORANGE, offset + 500 + 500, 600, size, size));
			walls.add(new Wall(Color.ORANGE, offset + 450 + 550, 600, size, size));
			walls.add(new Wall(Color.ORANGE, offset + 450 + 500, 600, size, size));
			walls.add(new Wall(Color.ORANGE, offset + 450 + 450, 600, size, size));

		}

		else if (map ==3)
		{
			
			for (int i=0; i<5; i++)
			{
				walls.add(new Wall(Color.BLUE, offset + i*50, 600, size, size));
			}
			for (int i=0; i<4; i++)
			{
				walls.add(new Wall(Color.BLUE, offset + 50 + i*50, 550, size, size));
			}
			for (int i=0; i<3; i++)
			{
				walls.add(new Wall(Color.BLUE, offset + 100 + i*50, 500, size, size));
			}
			for (int i=0; i<3; i++)
			{
				walls.add(new Wall(Color.BLUE, offset + 150 + i*50, 450, size, size));
			}
			for (int i=0; i<3; i++)
			{
				walls.add(new Wall(Color.BLUE, offset + 500 + i*50, 600, size, size));
			}

		}	

		else if (map == 4)
		{
			
			for (int i= 0; i<5; i++)
			{
				walls.add(new Wall(Color.YELLOW, offset + i*50, 600, size, size));
			}
			for (int i= 0; i<3; i++)
			{
				walls.add(new Wall(Color.YELLOW, offset + 100 + i*50, 550, size, size));
			}
			for (int i= 0; i<2; i++)
			{
				walls.add(new Wall(Color.YELLOW, offset + 150 + i*50, 500, size, size));
			}
			for (int l= 0; l<2; l++)
			{
				for (int i=0; i<4; i++)
				{
					walls.add(new Wall(Color.YELLOW, offset + 350 + i*50, 450 + 50+l, size, size));
				}		
			}
		}

			else if (map == 5)
			{
				for (int i= 0; i<5; i++)
				{
					walls.add(new Wall(Color.pink, offset + i*150, 600, size, size));
				}
			}	
		
	}	
	//Send Graphics to this method when its called
	public void paint(Graphics g)
	{

		super.paint(g);
		//Cast the graphics to 2D
		Graphics2D g2d = (Graphics2D) g;
		if (State == STATE.GAME)
		{
		//Draw a player
		player1.draw(g2d);
		
		//Draw the wall
		for(Wall wall: walls) wall.draw(g2d);
		
		//Draw Buttons
		g2d.setColor(Color.BLACK);
		//r
		g2d.drawRect(550, 25, 50, 50);
		//m
		g2d.drawRect(600, 25, 50, 50);
		//bar
		g2d.drawRect(25, 25, 500, 50);

	
		g2d.setColor(Color.WHITE);
		//r
		g2d.fillRect(551, 26, 48, 48);
		//m
		g2d.fillRect(601, 26, 48, 48);
		//score
		g2d.fillRect(25, 26, 500, 48);


		g2d.setColor(Color.BLACK);
		g2d.setFont(font);
		g2d.drawString("R", 564, 60);
		g2d.drawString("M", 614, 60);
		
		g2d.drawString("Life", 240,60);
		g2d.drawString("Score", 26,60);
		String scoreString = Integer.toString(score);
		String lifeString = Integer.toString(life);			
		g2d.drawString(scoreString, 115,60);
		g2d.drawString(lifeString, 320,60);
		
		}
		else if (State == STATE.MENU)
		{
			menu.render(g2d);
		}
			
	}
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{			
	}

	//Methods
	
	public void keyPressed(KeyEvent e) 
	{

		char plrKey = Character.toUpperCase(e.getKeyChar());

		if(plrKey == 'A') {

			command = "MOVE LEFT \n";
			out.print(command);
			out.flush();
			response = in.nextLine();
			System.out.println(response);
			player1.kLeft = Boolean.parseBoolean(response);
			
		}
		if(plrKey == 'D') {
 
			command = "MOVE RIGHT \n";
			out.print(command);
			out.flush();
			response = in.nextLine();
			System.out.println(response);
			player1.kRight = Boolean.parseBoolean(response);
			
			
		}
		if(plrKey == 'W') {
			command = "MOVE UP \n";
			out.print(command);
			out.flush();
			response = in.nextLine();
			System.out.println(response);
			player1.kUp = Boolean.parseBoolean(response);
			
			
		}
		if(plrKey == 'S') { 
			command = "MOVE DOWN \n";
			out.print(command);
			out.flush();
			//response = in.nextLine();
			response2 = in.next();
			//System.out.println(response);
			System.out.println(response2);	
			response2 = in.next();		
			System.out.println(response2);
			//player1.kDown = Boolean.parseBoolean(response);
			
		}
		if(plrKey == 'R') {
			restart();
			
		}

		
	}

	public void keyReleased(KeyEvent e) 
	{
		char plrKey = Character.toUpperCase(e.getKeyChar());

		if(plrKey == 'A') {
			player1.kLeft = false;	
		}
		if(plrKey == 'D') {
			player1.kRight = false; 
		}
		if(plrKey == 'W') {
			player1.kUp = false;
		}
		if(plrKey == 'S') {
			player1.kDown = false;
		}
		if(plrKey == 'R') {
			restart();			
		}
		if(plrKey == 'M') {		
			changeGameState();			
		}

			
	}

	public void changeGameState()
	{
		if (State == STATE.GAME) 
		{
			State = STATE.MENU;
		}
		else
		State = STATE.GAME;		
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("Click");
		if (Reset.contains(new Point(e.getPoint().x, e.getPoint().y-27))) reset();	
		if (Smenu.contains(new Point(e.getPoint().x, e.getPoint().y-27))) changeGameState();
        Point location = MouseInfo.getPointerInfo().getLocation();
        double x = location.getX();
        double y = location.getY();
        
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("State" +State);
         
        
        if (Menu.play.contains(new Point(e.getPoint().x, e.getPoint().y-27))) changeGameState();		
        if (Menu.help.contains(new Point(e.getPoint().x, e.getPoint().y-27))) changeGameState();
        if (Menu.quit.contains(new Point(e.getPoint().x, e.getPoint().y-27))) System.exit(0);;     
		
	}
}
