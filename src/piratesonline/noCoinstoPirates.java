package piratesonline;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class noCoinstoPirates {
	static int SOCKET_PORT;
	static Socket s;

	public static String userName;
	
	public static void main(String[] args) throws IOException
	{	
		SOCKET_PORT = 5556;
		s = new Socket("127.0.0.1", SOCKET_PORT);
		
		//initialize an output stream
		OutputStream outstream = s.getOutputStream();
		PrintWriter out = new PrintWriter(outstream);
		
		//initialize an input stream to receive data back from the server
		InputStream instream = s.getInputStream();
		Scanner in = new Scanner(instream);	
	
		
		
		//Screen Frame
		Frame frame = new Frame();
		frame.setSize(700,700);
		
		
		
		
		//Dimension of the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()/2 - frame.getSize().getWidth()/2), 
		(int)(screenSize.getHeight()/2 - frame.getSize().getHeight()/2));
		
		//Frame title and basic conf
		frame.setResizable(false);
		frame.setTitle("No Coins to Pirates");
		frame.setVisible(true);

		//close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		//close the socket
		
	}	
}
