package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import database.InsertRecords;
import database.SelectRecords;
import database.Update;

public class gService implements Runnable {
	
	private Socket s;
	private Scanner in;
	private PrintWriter out;

    SelectRecords select = new SelectRecords();
	InsertRecords insert = new InsertRecords(); 
	Update update = new Update(); 
	
	public gService(Socket temp1) {
		s = temp1;
	}
	
	public void run() {
		try {
			in = new Scanner(s.getInputStream());
			out = new PrintWriter(s.getOutputStream());
			processRequest();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processRequest() throws IOException {
		while(true) {
			if ( !in.hasNext() ) { return; }
			String command = in.next();
			
			if(command.equals("QUIT")) { return; }
			executeCommand(command);
		}
	}
	
	private void executeCommand(String command) {


		if(command.equals("SAVESCORE")) {
			

			int id = in.nextInt();
			int score = in.nextInt();
			
			update.UpdateDistance(id, score);

			out.println("SAVING SOCRE");
			
			
			
		}
		
		

		if(command.equals("SAVEDEATH")) {
			

			int id = in.nextInt();
			
			update.UpdateDeaths(id);

			out.println("SAVING DEATHS");
			
			
			
		}

		
		if(command.equals("NEWPLAYER")) {

			String name = in.next();
			

			insert.addUser(name); 

			Integer newID = select.getUserId(name);

			out.println(newID);	
			
			
		}
		
		
		
		if (command.equals ("MOVE"))
		{
			String direction = in.next();

			if (direction.equals ("DOWN")) {
				out.println("MOVE DOWN");				
			}else {
				out.println("true");
			}
			

				 
		}
		
		
		
		out.flush();
	}

}
