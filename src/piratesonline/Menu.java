package piratesonline;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;


public class Menu {
	
	public static Rectangle play = new Rectangle(290, 150,100,50);
	public static Rectangle help = new Rectangle(290, 250,100,50);
	public static Rectangle quit = new Rectangle(290, 350,100,50);
//	public static Rectangle chart = new Rectangle(200, 450,300,200);
	public static OutputStream outstream;
	public static PrintWriter out;
	public static InputStream instream;
	public static Scanner in;
	public static String response;
	public static String TopName, TopScore;
    public static String[][] newPlrList = new String[10][2];
    public static ArrayList<String> list;

	public static JTable table;
    public static String command;
    public static String ResString;
    
	public static void render(Graphics g)
	{


		Graphics2D g3d = (Graphics2D) g;
		Font fnt0 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("NO COINS TO PIRATES", 175,100);
		
		Font fnt1 = new Font("arial", Font.BOLD,30);
		g.setFont(fnt1);
		g.drawString("Play", play.x+20, play.y+35);
		g3d.draw(play);
		
		g.drawString("Help", help.x+20, help.y+35);
		g3d.draw(help);
		g.drawString("Quit", quit.x+20, quit.y+35);
		g3d.draw(quit);
	
	}
	
}
